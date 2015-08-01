package main.java.model.interfaces;

import java.net.URL;

public abstract class WebPage implements Serializer {
	
	protected URL url;
	protected Integer id;
	protected String name;
	
	public WebPage(URL url, Integer id, String name){
		this.url = url;
		this.id = id;
		this.name = name;
	}
	
	public String extractInformation() {
		// TODO Auto-generated method stub
		return this.serialize().toString();
	}
}
