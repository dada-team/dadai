package main.java.model.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import main.java.model.interfaces.Serializer;

public class FinishList implements Serializer {
	private List<Finish> finishes = new ArrayList<Finish>();

	@Override
	public JsonElement serialize() {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		for (Finish finish : finishes) {
			result.add("place", new JsonPrimitive(finish.place));
			result.add("cote", new JsonPrimitive(finish.cote));
			result.add("horse", new JsonPrimitive(finish.horse.extractInformation()));
		}

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
