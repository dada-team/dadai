package main.java.model.impl;

import java.util.HashMap;

public class Prediction {

	private String predicter;
	private HashMap<String, Integer> horseRankPrediction = new HashMap();
	
	public Prediction(String predicter, HashMap<String, Integer> horseRankPrediction) {
		this.predicter = predicter;
		this.horseRankPrediction = horseRankPrediction;
	}
}
