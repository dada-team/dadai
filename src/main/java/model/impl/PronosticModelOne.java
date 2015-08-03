package main.java.model.impl;

import java.net.URL;

public class PronosticModelOne extends Pronostic {
	private Float poids;
	private Integer corde;
	private String oeilleres;
	
	public PronosticModelOne(String horseName, Float poids, Integer corde, String oeilleres) {
		super(horseName);
		this.poids = poids;
		this.corde = corde;
		this.oeilleres = oeilleres;
	}
}
