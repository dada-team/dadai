package main.java.controllers.data;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
		
		String sex = doc.select(WpHorseParameters.HORSE_SEX_SELECT).get(0).select("td:eq(4)").get(0).text();
		String age = doc.select(WpHorseParameters.HORSE_AGE_SELECT).get(1).select("td:eq(4)").get(0).text();		
		
		
		Elements horsesRanks = doc.select(WpHorseParameters.HORSE_MUSIC_SELECT);
		
		Integer horseId = null;
		
		Pattern pattern = Pattern.compile(WpHorseParameters.URL_HORSE_ID_EXTRACT);//.html?idcourse=([0-9]+)
		java.util.regex.Matcher matcher = pattern.matcher(url.toString());

		if (matcher.find()) {
			horseId = Integer.parseInt(matcher.group(1));
		} 

		logger.debug("sex : " + sex);
		logger.debug("age : " + age);
		logger.debug("name : " + horseName);
		logger.debug("id : " + horseId);
		
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
