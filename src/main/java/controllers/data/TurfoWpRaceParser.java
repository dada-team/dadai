package main.java.controllers.data;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hamcrest.Matcher;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import main.java.controllers.interfaces.WpParser;
import main.java.model.impl.Finish;
import main.java.model.impl.FinishList;
import main.java.model.impl.WpHorseTurfo;
import main.java.model.impl.WpRaceTurfo;
import main.java.model.interfaces.WebPage;

public class TurfoWpRaceParser implements WpParser {

	Logger logger = Logger.getLogger("main.java.controllers.sniffers.TurfoWpRaceParser");
	TurfoWpHorseParser horseParser;

	public TurfoWpRaceParser() {
		this.horseParser = new TurfoWpHorseParser();
	}

	@Override
	public WebPage parse(URL url) throws IOException {
		// TODO Auto-generated method stub
		logger.info("... parse : " + url);
		Document doc = Jsoup.connect(url.toString()).timeout(WpRaceParameters.TIMED_OUT_MILLI).get();
		Elements horsesUrl = doc.select(WpRaceParameters.HORSE_URL_SELECT);
		Elements horsesCotes = doc.select(WpRaceParameters.HORSE_COTES_SELECT);
		Elements horsesJockey = doc.select(WpRaceParameters.HORSE_JOCKEY_SELECT);
		// Elements horsesUrl = doc.select("table.tableauLine:eq(0) tbody tr
		// td:eq(3) a[href]");

		FinishList fl = new FinishList();

		for (int i = 0; i < horsesUrl.size(); i++) {
			try {
				int rang = (i + 1);
				Float cote = Float.parseFloat(horsesCotes.get(i).text());
				String horseURL = horsesUrl.get(i).attr("abs:href").trim().toString();
				String horseJockey = horsesJockey.get(i).attr("abs:href").trim().toString();
				
				logger.debug("___________________________");
				logger.debug("... new finish");
				logger.debug("... rank : " + rang);
				logger.debug("... cote : " + cote);
				logger.debug("... horse found : " + horseURL);
				logger.debug("... jockey found : " + horseJockey);
				logger.debug("___________________________");
				
				WebPage horseWebPage = this.horseParser.parse(new URL(horseURL));
	
				Finish finish = new Finish(i, cote, horseJockey, horseWebPage);
				
				fl.getFinishes().add(finish);
			} catch(Exception e){
				logger.error(e);
			}
		}

		// http://www.turfomania.fr/pronostics/rapports-dimanche-12-juillet-2015-chantilly-prix-de-l-hermitage.html?idcourse=191143
		WebPage wp = initWebPage(url, fl);
		logger.info("_________________________");
		return wp;
	}

	public WpRaceTurfo initWebPage(URL url) {
		Pattern pattern = Pattern.compile(WpRaceParameters.URL_RACE_PATTERN_REGEXP);// .html?idcourse=([0-9]+)
		java.util.regex.Matcher matcher = pattern.matcher(url.toString());

		Integer id = null;
		DateTime dt = null;
		String nameCourse = null;

		try {
			if (matcher.find()) {
				dt = transformToDate(matcher.group(1));
				nameCourse = matcher.group(2);
				id = Integer.parseInt(matcher.group(3));
			}
		} catch (Exception e) {
			logger.debug("error parsing url race");
			logger.debug(e);
		}
		logger.debug("___________________________");
		logger.debug("... new webpage");
		logger.debug("... url : " + url);
		logger.debug("... id : " + id);
		logger.debug("... nameCourse : " + nameCourse);
		logger.debug("... date : " + dt);
		logger.debug("___________________________");
		
		WpRaceTurfo wp = new WpRaceTurfo(url, id, nameCourse, dt, null);

		return wp;
	}

	public WpRaceTurfo initWebPage(URL url, FinishList fl) {
		WpRaceTurfo wp = initWebPage(url);
		wp.setFinishList(fl);
		return wp;
		// http://www.turfomania.fr/pronostics/rapports-dimanche-12-juillet-2015-chantilly-prix-de-l-hermitage.html?idcourse=191143
	}

	private DateTime transformToDate(String date) {
		// TODO Auto-generated method stub
		String[] parts = date.split("-");
		String day = getDay(parts[0]); // 004
		String month = getMonth(parts[1]); // 034556
		Integer year = Integer.parseInt(parts[2]);

		String strDateTime = day + "/" + month + "/" + year;
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/YYYY");
		DateTime dateTime = DateTime.parse(strDateTime, fmt);

		return dateTime;
	}

	private String getDay(String day) {
		if (day.length() == 1)
			day = "0" + day;

		return day;
	}

	private String getMonth(String month) {
		// TODO Auto-generated method stub
		String[] monthNames = { "janvier", "fevrier", "mars", "avril", "mai", "juin", "juillet", "aout", "septembre",
				"octobre", "novembre", "decembre" };
		Integer indexFound = null;

		for (int i = 1; i <= monthNames.length; i++) {
			if (month.equalsIgnoreCase(monthNames[i - 1])) {
				indexFound = i;
				break;
			}
		}

		String indexReturned = Integer.toString(indexFound);

		if (indexReturned.length() == 1)
			indexReturned = "0" + indexReturned;

		return indexReturned;
	}

}
