package main.java.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class WpHorseTurfo {

	private String name;
	private DateTime age;
	private String sex;
	private List<String> lastPerformances = new ArrayList<String> ();
	
	
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
}
