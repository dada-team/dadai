package main.java.controllers.data;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import main.java.controllers.interfaces.WpParser;
import main.java.model.impl.WpHorseTurfo;
import main.java.model.interfaces.WebPage;


public class TurfoWpHorseParser implements WpParser {

	Logger  logger = Logger.getLogger("main.java.controllers.sniffers.TurfoWpHorseParser");
	
	@Override
	public WebPage parse(URL url) throws IOException {
		// TODO Auto-generated method stub
		logger.info("***************************");
		logger.info("PARSE : " + url);
		Document doc = Jsoup.connect(url.toString()).get();
		String path = url.getPath();
		String horseName = path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('.'));
		
		String sex = doc.select("div#ficheStatsLong table.tableauLine tbody tr").get(0).select("td:eq(4)").get(0).text();
		String age = doc.select("div#ficheStatsLong table.tableauLine tbody tr").get(1).select("td:eq(4)").get(0).text();		
		
		logger.debug("sex " + sex);
		logger.debug("age " + age);
		logger.debug("name" + horseName);
		
		Elements horsesRanks = doc.select("div.ficheStatsMusiqueNew a");
		
		List<String> rs = new ArrayList<String> ();
		
		for (int i = 0; i < horsesRanks.size() ; i++ ) {
			logger.debug("URL races found : " + horsesRanks.get(i).attr("abs:href").trim().toString());
			String horseRes = horsesRanks.get(i).text().trim();
			logger.debug("result : " + horseRes);
			rs.add(horseRes);
		}

		
		WebPage wp = new WpHorseTurfo(url, null, horseName, age, sex, rs);
		//Elements horsesUrl = doc.select("table.tableauLine:eq(0) tbody tr td:eq(3) a[href]");
		return wp;
	}

}
