package main.java.controllers.data;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import main.java.controllers.interfaces.WpManager;
import main.java.controllers.interfaces.WpParser;
import main.java.model.interfaces.WebPage;

public class WpRaceManager extends WpManager  {

	private WpParser parser = new TurfoWpRaceParser();
	private List<WebPage> webpages = new ArrayList<WebPage> ();
	
	@Override
	public void importWebPagesData(List<URL> urls) throws IOException {
		// TODO Auto-generated method stub
		for (URL url : urls) {
			importWebPageData(url);
		}
	}

	@Override
	public void writeToJSON(File o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void importWebPageData(URL url) throws IOException {
		addWebPage(parser.parse(url));
	}
	
	private void addWebPage(WebPage wp) {
		this.webpages.add(wp);
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}


}
