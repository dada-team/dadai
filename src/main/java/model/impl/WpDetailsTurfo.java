package main.java.model.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import main.java.model.interfaces.WebPage;

public abstract class WpDetailsTurfo extends WebPage {

	private Prediction prediction;
	private List<Pronostic> pronostics = new ArrayList<Pronostic> ();
	
	public WpDetailsTurfo(URL url, Integer id, String name) {
		super(url, id, name);
		// TODO Auto-generated constructor stub
	}

	public List<Pronostic> getPronostics() {
		return pronostics;
	}

	public void setPronostics(List<Pronostic> pronostics) {
		this.pronostics = pronostics;
	}

	public Prediction getPrediction() {
		return prediction;
	}

	public void setPrediction(Prediction prediction) {
		this.prediction = prediction;
	}
}
