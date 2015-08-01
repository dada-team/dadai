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

	Logger  logger = Logger.getLogger("main.java.controllers.sniffers.TurfoWpRaceParser");
	TurfoWpHorseParser horseParser;
	
	public TurfoWpRaceParser(){
		this.horseParser = new TurfoWpHorseParser();
	}
	
	@Override
	public WebPage parse(URL url) throws IOException {
		// TODO Auto-generated method stub
		logger.info("***************************");
		logger.info("PARSE : " + url);
		Document doc = Jsoup.connect(url.toString()).get();
		Elements horsesUrl = doc.select("table.tableauLine tbody tr td:eq(2) a[href]");
		Elements horsesCotes = doc.select("table.tableauLine tbody tr td:eq(6)");
		//Elements horsesUrl = doc.select("table.tableauLine:eq(0) tbody tr td:eq(3) a[href]");
		
		FinishList fl = new FinishList();
		
		for (int i = 0; i < horsesUrl.size() ; i++ ) {
			int rang = (i + 1);
			Float cote = Float.parseFloat(horsesCotes.get(i).text());
			URL horseURL = new URL(horsesUrl.get(i).attr("abs:href").trim().toString());
			WebPage horseWebPage = this.horseParser.parse(horseURL);
			
			Finish finish = new Finish(i, cote, horseWebPage);
			logger.debug("Rank : " + rang);
			logger.debug("URL horses found : " + horsesUrl.get(i).attr("abs:href").trim().toString());
			fl.getFinishes().add(finish);
			logger.debug("Cote : " + horsesCotes.get(i).text());
		}
	
		logger.info("***************************");
		//http://www.turfomania.fr/pronostics/rapports-dimanche-12-juillet-2015-chantilly-prix-de-l-hermitage.html?idcourse=191143
		WebPage wp = initWebPage(url, fl);
		return wp;
	}
	
	public WebPage initWebPage(URL url, FinishList fl) {
		String line = "This order was placed for QT3000! OK?";
		Pattern pattern = Pattern.compile(".*/rapports-[^-]+-([^-]+-[^-]*-[^-]*)-(.+).html?idcourse=(\\d+)");
		java.util.regex.Matcher matcher = pattern.matcher(line);
		
		Integer id = null;
		DateTime dt = null;
		String nameCourse = null;
		
		if (matcher.find()) {
			dt = transformToDate(matcher.group(0));
			nameCourse = matcher.group(1);
		   	id = Integer.parseInt(matcher.group(2));
		}
		
		WebPage wp = new WpRaceTurfo(url, id, nameCourse, dt, fl);
		return wp;
		//http://www.turfomania.fr/pronostics/rapports-dimanche-12-juillet-2015-chantilly-prix-de-l-hermitage.html?idcourse=191143
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
		String[] monthNames = {"janvier", "fevrier", "mars", "avril", "mai", "juin", "juillet", "aout", "septembre", "octobre", "novembre", "decembre"};
	    Integer indexFound = null;
		
		for( int i = 1; i <= monthNames.length; i++)
		{
		    if (month.equalsIgnoreCase(monthNames[i-1])) {
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
