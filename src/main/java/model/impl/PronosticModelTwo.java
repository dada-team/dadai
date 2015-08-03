package main.java.model.impl;

import org.joda.time.DateTime;

public class PronosticModelTwo extends Pronostic {
	private DateTime dt;
	private Integer distance;
	
	public PronosticModelTwo(String horseName, DateTime dt, Integer distance) {
		super(horseName);
		this.dt = dt;
		this.distance = distance;
		// TODO Auto-generated constructor stub
	}
}
