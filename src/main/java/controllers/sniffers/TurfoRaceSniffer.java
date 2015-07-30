package main.java.controllers.sniffers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.java.controllers.interfaces.Sniffer;

public class TurfoRaceSniffer implements Sniffer<URL> {
	
	Logger  logger = Logger.getLogger("main.java.controllers.sniffers.TurfoRaceSniffer");
	
	private DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
	private TurfoRaceParameters parameters;

	
	public TurfoRaceSniffer() {
		  this.parameters = new TurfoRaceParameters();
	}
	
	/**
	 * 
	 */
	@Override
	public List<URL> sniff(DateTime dDebut, DateTime dFin) throws Exception {
		
		List<URL> urlDateFound = new ArrayList<URL> ();
		URIBuilder ub = new URIBuilder(getMainUrl().toString());
		logger.info("********************************");
		logger.info("SNIFF");
		logger.debug("Main site URL : " + ub.toString());
		DateTime currentDate = dDebut;	
		
		while(currentDate.isBefore(dFin) || currentDate.equals(dFin)) {
			ub.addParameter(parameters.getUrlGetDateArg(), currentDate.toString(dtf));
			
			logger.debug("Main race URL sniffed : " + ub.build().toURL());
			Document doc = Jsoup.connect(ub.build().toString()).get();
			urlDateFound.addAll(extractAllRaceURL(doc));
			
			currentDate = currentDate.plusDays(1);
			ub.clearParameters();
		}
		logger.info("********************************");
		return urlDateFound;
	}
	

	/**
	 * @throws Exception 
	 * 
	 */
	@Override
	public List<URL> sniff() throws Exception {
		DateTime date = new DateTime();
		return sniff(date, date);
	}
	
	/**
	 * 
	 * @param doc
	 * @return
	 * @throws MalformedURLException
	 */
	private List<URL> extractAllRaceURL(Document doc) throws MalformedURLException {
		List<URL> urlExtracted = new ArrayList<URL> ();
		Elements links = doc.select(parameters.getRaceUrlRegexp());
		
		for (Element link : links) {
			logger.debug("URL races found : " + link.attr("abs:href").trim() );
			urlExtracted.add(new URL(link.attr("abs:href").trim())); 
		}
		
		return urlExtracted;
	}


	/**
	 * 
	 */
	private URL getMainUrl() throws MalformedURLException {
		// TODO Auto-generated method stub
		return new URL(parameters.getMainRaceUrl());
	}
}
