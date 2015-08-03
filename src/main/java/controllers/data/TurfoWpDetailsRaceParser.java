package main.java.controllers.data;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import main.java.controllers.interfaces.WpParser;
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
		
		// 1 : caracteristiques chevaux
		Elements horsesPredictions = doc.select("table.tablesorter.tableauLine tbody tr");
		Elements jockeyWeights = doc.select("table.tablesorter.tableauLine tbody tr");
		Elements horsesCordes = doc.select("table.tablesorter.tableauLine tbody tr");
		Elements horsesOeilleres = doc.select("table.tablesorter.tableauLine tbody tr");

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
		
			logger.debug("name : " + name);
			logger.debug("weight : " + weight);
			logger.debug("corde : " + corde);
			logger.debug("oeilleres : " + oeilleres);
		}
		
		// 2 : prognostics
		String patternStringRankHorse = "([0-9]+)(.+)";
		Pattern patternH = Pattern.compile(patternStringRankHorse);
		
		Elements expertPredictions = doc.select("div.colTreeDetailBloc2 div.choixJeux");
		for (int i = 0; i < horsesPredictions.size(); i++) {
			int rank = (i+1);
			String horseName = expertPredictions.get(i).text();
			java.util.regex.Matcher matcherH = patternH.matcher(horseName);
			
			if (matcherH.find()) {
				horseName = matcherH.group(2);
			} else {
				logger.warn("... no prediction for the horse");
			}
			
			logger.debug("Rank " + rank);
			logger.debug("Horse : " + horseName);
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
		
		logger.debug("Predicteur : " + expertPredicter);
		
		return null;
	}

}
