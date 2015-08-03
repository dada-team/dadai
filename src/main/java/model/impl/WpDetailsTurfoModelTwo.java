package main.java.model.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.gson.JsonElement;

public class WpDetailsTurfoModelTwo extends WpDetailsTurfo {
		
	public WpDetailsTurfoModelTwo(URL url, Integer id, String name) {
		super(url, id, name);
		// TODO Auto-generated constructor stub
	}
	
	public void addPronostic(String horseName, DateTime dt, Integer distance) {
		this.getPronostics().add(new PronosticModelTwo(horseName, dt, distance));
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

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}


}
