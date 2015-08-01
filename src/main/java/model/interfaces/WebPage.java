package main.java.model.interfaces;

import java.net.URL;

public abstract class WebPage implements Serializer {
	
	protected URL url;
	protected Integer id;
	protected String name;
	
	abstract public String extractInformation();
}
