package main.java.controllers.interfaces;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

import main.java.model.interfaces.WebPage;

public abstract class WpManager {

	private List<WebPage> webPages;
	
	public abstract void importWebPagesData(List<URL> url) throws IOException;
	public abstract void importWebPageData(URL url) throws IOException;
	public abstract void writeToJSON(File o) throws IOException;
	public abstract void clear();
	public abstract void print();
	public List<WebPage> getWebPages() {
		return webPages;
	}
	public void setWebPages(List<WebPage> webPages) {
		this.webPages = webPages;
	}
}
