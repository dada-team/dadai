package main.java.model.impl;

import org.joda.time.DateTime;

import com.google.gson.JsonElement;

import main.java.model.interfaces.Serializer;
import main.java.model.interfaces.WebPage;

public class Finish implements Serializer {

	private Integer place;
	private Float cote;
	WebPage horse;
	
	public Finish() {
		
	}

	@Override
	public JsonElement serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deserialize() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
