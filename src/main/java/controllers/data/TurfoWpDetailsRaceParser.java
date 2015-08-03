package main.java.controllers.data;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import main.java.controllers.interfaces.WpParser;
import main.java.model.impl.Prediction;
import main.java.model.impl.Pronostic;
import main.java.model.impl.WpDetailsTurfoModelOne;
import main.java.model.impl.WpDetailsTurfoModelTwo;
import main.java.model.interfaces.WebPage;

public class TurfoWpDetailsRaceParser extends WpParser {

	Logger logger = Logger.getLogger("main.java.controllers.sniffers.TurfoWpDetailsParser");
	
	public TurfoWpDetailsRaceParser(boolean initProxies) {
		super(initProxies);
		// TODO Auto-generated constructor stub
	}

	@Override
	public WebPage parse(URL url) throws IOException, InterruptedException, Exception {
		// TODO Auto-generated method stub
		Document doc = Jsoup.parse(this.download(url));
		Elements headers = doc.select("table.tablesorter.tableauLine thead th");
		return parse(url, headers, doc);
	}
	
	private WebPage parse(URL url, Elements headers, Document doc) {
		// 1 : caracteristiques chevaux
		WebPage wp = null;
		if (isFirstModel(headers))
			wp = parseDocFirstModel(url, doc);
		else if (isSecondModel(headers))
			wp = parseDocSecondModel(url, doc);
		return wp;
	}

	private boolean isSecondModel(Elements headers) {
		// TODO Auto-generated method stub
		String distance = headers.get(2).text();
		String record = headers.get(7).text();
		
		if(distance.equals("Dist.") && record.equals("Record"))
			return true;
		else
			return false;
	}
	
	private boolean isFirstModel(Elements headers) {
		// TODO Auto-generated method stub
		String poids = headers.get(2).text();
		String cordes = headers.get(3).text();
		String oeilleres = headers.get(8).text();
		
		if (poids.equals("Poids") && cordes.equals("Corde") && oeilleres.equals("Oeill."))
			return true;
		else
			return false;
	}

	private WebPage parseDocFirstModel (URL url, Document doc) {
		Elements horsesPredictions = doc.select("table.tablesorter.tableauLine tbody tr");
		Elements jockeyWeights = doc.select("table.tablesorter.tableauLine tbody tr");
		Elements horsesCordes = doc.select("table.tablesorter.tableauLine tbody tr");
		Elements horsesOeilleres = doc.select("table.tablesorter.tableauLine tbody tr");
		
		WpDetailsTurfoModelOne wp = new WpDetailsTurfoModelOne(url, null, null);

		for (int i = 0; i < horsesPredictions.size(); i++) {
			String name = horsesPredictions.get(i).select("td:eq(1) a").text().trim().toString();
			Float weight = null;
			
			try {
				weight = Float.parseFloat(jockeyWeights.get(i).select("td:eq(4)").text().trim().toString().replaceAll(",", "."));
			} catch (Exception e) {
				logger.error(e);
			}
			
			Integer corde = null;
			try {
				corde = Integer.parseInt(horsesCordes.get(i).select("td:eq(5)").text().trim().toString());
			} catch (Exception e) {
				logger.error(e);
			}
			String oeilleres = horsesOeilleres.get(i).select("td:eq(10)").text().trim().toString();
		
			logger.debug("... name : " + name);
			logger.debug("... weight : " + weight);
			logger.debug("... corde : " + corde);
			logger.debug("... oeilleres : " + oeilleres);
			
			wp.addPronostic(name, weight, corde, oeilleres);
		}
		
		wp.setPrediction(getPrediction(doc));
		
		return wp;
	}
	

	private WebPage parseDocSecondModel(URL url, Document doc) {
		// TODO Auto-generated method stub
		Elements horsesDistance = doc.select("table.tablesorter.tableauLine tbody tr");
		Elements horsesRecord = doc.select("table.tablesorter.tableauLine tbody tr");
		WpDetailsTurfoModelTwo wp = new WpDetailsTurfoModelTwo(url, null, null);
		
		for (int i = 0; i < horsesDistance.size(); i++) {
			String name = horsesDistance.get(i).select("td:eq(1) a").text().trim().toString();
			Integer distance = Integer.parseInt(horsesDistance.get(i).select("td:eq(4)").text().trim().toString());
			
			String time = horsesRecord.get(i).select("td:eq(9)").text().trim().toString();
			String timePatternParsing = "([0-9]+)'([0-9]+)\"([0-9]+)";
			Pattern patternTime = Pattern.compile(timePatternParsing);
			java.util.regex.Matcher matcherTime = patternTime.matcher(time);
			
			DateTime dt = null;
			
			if (matcherTime.find()) {
				dt = new DateTime(0, 0, 0, 0, Integer.parseInt(matcherTime.group(1)), Integer.parseInt(matcherTime.group(2)), Integer.parseInt(matcherTime.group(3)));
			} else {
				logger.warn("... time is at a weird format");
			}
			
			wp.addPronostic(name, dt, distance);
		}
		
		wp.setPrediction(getPrediction(doc));
		
		return wp;
	}
	
	private Prediction getPrediction(Document doc) {
		String patternStringRankHorse = "([0-9]+)(.+)";
		Pattern patternH = Pattern.compile(patternStringRankHorse);
		
		Elements expertPredictions = doc.select("div.colTreeDetailBloc2 div.choixJeux");
		HashMap<String, Integer> horseRankPrediction = new HashMap<String, Integer>();
		
		for (int i = 0; i < expertPredictions.size(); i++) {
			int rank = (i+1);
			String horseName = expertPredictions.get(i).text();
			java.util.regex.Matcher matcherH = patternH.matcher(horseName);
			
			if (matcherH.find()) {
				horseName = matcherH.group(2);
			} else {
				logger.warn("... no prediction for the horse");
			}
			
			horseRankPrediction.put(horseName, rank);
			logger.debug("... rank " + rank);
			logger.debug("... horse : " + horseName);
		}
		
		// 3 : predicteur
		String expertPredicter = doc.select("div.choixPronotiqueur").get(0).text().toString();
	
		String patternStringPredicter = "(Pronostiqué par)(.+)";
		Pattern patternP = Pattern.compile(patternStringPredicter);// .html?idcourse=([0-9]+)
		java.util.regex.Matcher matcherP = patternP.matcher(expertPredicter);
	
		if (matcherP.find()) {
			expertPredicter = matcherP.group(2);
		} else {
			logger.warn("... impossible to catch predicter name or no predictor");
			//throw new Exception("... impossible to catch predicter name");
		}
		
		logger.debug("... predicteur : " + expertPredicter);
		Prediction p = new Prediction(expertPredicter, horseRankPrediction);
		
		return p;
	}

}
