package main.java.model.interfaces;

import java.net.URL;

import org.joda.time.DateTime;

public abstract class WebPage implements Serializer {
	
	protected URL url;
	protected Integer id;
	protected String name;
	protected DateTime creationTime;
	
	public WebPage(URL url, Integer id, String name){
		this.url = url;
		this.id = id;
		this.name = name;
		this.creationTime = DateTime.now();
	}
	
	public String extractInformation() {
		// TODO Auto-generated method stub
		return this.serialize().toString();
	}
}
