package main.java.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.gson.JsonElement;

import main.java.model.interfaces.Serializer;
import main.java.model.interfaces.WebPage;

public class WpRaceTurfo extends WebPage implements Serializer {

	private DateTime dtEvent;
	private List<Finish> finish = new ArrayList<Finish> ();
	
	@Override
	public void extractInformation() {
		// TODO Auto-generated method stub
		
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
