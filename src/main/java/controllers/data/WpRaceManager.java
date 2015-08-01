package main.java.controllers.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

import main.java.controllers.interfaces.WpManager;
import main.java.controllers.interfaces.WpParser;
import main.java.model.interfaces.WebPage;

public class WpRaceManager extends WpManager {

	private WpParser parser = new TurfoWpRaceParser();

	public WpRaceManager(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	@Override
	public void importWebPagesData(List<URL> urls) throws IOException {
		// TODO Auto-generated method stub
		for (URL url : urls) {
			importWebPageData(url);
		}
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
		this.webPages.add(wp);
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

	}

	public void writeToJSON() throws FileNotFoundException {
		for (WebPage webpage : webPages) {
			PrintWriter out = new PrintWriter(outputDirectory + webpage.getFileName());
			out.println(webpage.extractInformation());
			out.close();
		}
		// TODO Auto-generated method stub

	}

}
