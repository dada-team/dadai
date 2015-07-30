package main.java.model.interfaces;

import java.net.URL;

public abstract class WebPage {
	
	private URL url;
	private Integer id;
	private String name;
	
	abstract public void extractInformation();
}
