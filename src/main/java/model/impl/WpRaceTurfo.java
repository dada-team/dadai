package main.java.model.impl;

import java.net.URL;
import java.security.Timestamp;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import main.java.model.interfaces.WebPage;

public class WpRaceTurfo extends WebPage {

	private DateTime dtEvent;
	private FinishList finishList;


	public WpRaceTurfo(URL url, Integer id, String name, DateTime dtEvent, FinishList finishList) {
		super(url, id, name);
		this.dtEvent = dtEvent;
		this.finishList = finishList;
		// TODO Auto-generated constructor stub
	}

	@Override
	public JsonElement serialize() {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		result.add("id", new JsonPrimitive(this.id));
		result.add("name", new JsonPrimitive(this.name));
		result.add("url", new JsonPrimitive(this.url.toString()));
		
		String pattern = "yyyy-MM-dd";
		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		String formattedDate = formatter.print(this.dtEvent);
		
		result.add("date", new JsonPrimitive(formattedDate));
		result.add("results", this.finishList.serialize());

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
		return new StringBuilder().append(getDtEventFormatted()).append("_").append(name).append("_")
				.append(getCreationTimeFormatted()).append(".json").toString();
	}

	public DateTime getDtEvent() {
		return dtEvent;
	}

	public void setFinishList(FinishList finishList) {
		this.finishList = finishList;
	}
	
	
	public String getDtEventFormatted() {
		String pattern = "yyyy-MM-dd";
		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		String formatted = formatter.print(this.dtEvent);
		return formatted;
	}
}
