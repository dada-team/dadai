package main.java.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import main.java.model.interfaces.Serializer;
import main.java.model.interfaces.WebPage;

public class WpHorseTurfo extends WebPage {

	private String name;
	private DateTime age;
	private String sex;
	private List<String> lastPerformances = new ArrayList<String>();

	public DateTime getAge() {
		return age;
	}

	public void setAge(DateTime age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public List<String> getLastPerformances() {
		return lastPerformances;
	}

	public void setLastPerformances(List<String> lastPerformances) {
		this.lastPerformances = lastPerformances;
	}

	@Override
	public JsonElement serialize() {
		JsonObject result = new JsonObject();

		result.add("name", new JsonPrimitive(this.name));
		result.add("age", new JsonPrimitive(this.age.toString()));
		result.add("sex", new JsonPrimitive(this.sex));
		result.add("lastResults", new JsonPrimitive(this.lastPerformances.toString()));

		return result;
	}

	@Override
	public Object deserialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String extractInformation() {
		// TODO Auto-generated method stub
		return this.serialize().toString();
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return new StringBuilder().append(name).append("_").append(DateTime.now()).append(".json").toString();
	}

}
