package main.java.controllers.data;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import main.java.controllers.interfaces.WpParser;
import main.java.model.interfaces.WebPage;

public class TurfoWpRaceParser implements WpParser {

	Logger  logger = Logger.getLogger("main.java.controllers.sniffers.TurfoWpRaceParser");
	
	
	@Override
	public WebPage parse(URL url) throws IOException {
		// TODO Auto-generated method stub
		logger.info("***************************");
		logger.info("PARSE : " + url);
		Document doc = Jsoup.connect(url.toString()).get();
		Elements horsesUrl = doc.select("table.tableauLine tbody tr td:eq(2) a[href]");
		Elements horsesCotes = doc.select("table.tableauLine tbody tr td:eq(6)");
		//Elements horsesUrl = doc.select("table.tableauLine:eq(0) tbody tr td:eq(3) a[href]");
		
		for (int i = 0; i < horsesUrl.size() ; i++ ) {
			logger.debug("Rank : " + (i + 1));
			logger.debug("URL horses found : " + horsesUrl.get(i).attr("abs:href").trim().toString());
			logger.debug("Cote : " + horsesCotes.get(i).text());
		}
	
		logger.info("***************************");
		
		return null;
	}

}
