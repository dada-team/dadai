package main.java.model.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import main.java.model.interfaces.Serializer;
import main.java.model.interfaces.WebPage;

public class Finish implements Serializer {

	protected Integer place;
	protected Float cote;
	protected String jockey;
	protected WebPage horse;

	public Finish(Integer place, Float cote, String jockey, WebPage horse) {
		this.place = place;
		this.cote = cote;
		this.horse = horse;
		this.jockey = jockey;
	}

	@Override
	public JsonElement serialize() {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		result.add("place", new JsonPrimitive(place));
		result.add("cote", new JsonPrimitive(cote));
		result.add("horse", horse.serialize());
		result.add("jockey", new JsonPrimitive(jockey));
		
		return result;
	}

	@Override
	public Object deserialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

}
