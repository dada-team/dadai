package main.java.model.impl;

import java.net.URL;

import org.joda.time.DateTime;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import main.java.model.interfaces.WebPage;

public class WpRaceTurfo extends WebPage {

	public WpRaceTurfo(URL url, Integer id, String name, DateTime dtEvent, FinishList finishList) {
		super(url, id, name);
		this.dtEvent = dtEvent;
		this.finishList = finishList;
		// TODO Auto-generated constructor stub
	}

	private DateTime dtEvent;
	private FinishList finishList;

	@Override
	public JsonElement serialize() {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		result.add("id", new JsonPrimitive(this.id));
		result.add("name", new JsonPrimitive(this.name));
		result.add("url", new JsonPrimitive(this.url.toString()));
		result.add("date", new JsonPrimitive(this.dtEvent.toString()));
		result.add("finish", new JsonPrimitive(this.finishList.serialize().toString()));

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
		return new StringBuilder().append(dtEvent.toString()).append("_").append(name).append("_")
				.append(DateTime.now()).append(".json").toString();
	}

}
