package main.java.model.impl;

import org.joda.time.Period;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import main.java.model.interfaces.Serializer;
import main.java.model.interfaces.WebPage;

public class Finish implements Serializer {

	protected Integer place;
	protected Float cote;
	protected String jockey;
	protected String trainer;

	// details
	protected Pronostic pronostic;

	protected WebPage horse;

	public Finish(Integer place, Float cote, String jockey, String trainer, WebPage horse) {
		this.place = place;
		this.cote = cote;
		this.horse = horse;
		this.jockey = jockey;
		this.trainer = trainer;
	}

	@Override
	public JsonElement serialize() {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		result.add("place", new JsonPrimitive(place));

		if (cote != null)
			result.add("cote", new JsonPrimitive(cote));
		else
			result.add("cote", new JsonPrimitive(""));

		result.add("horse", horse.serialize());
		result.add("jockey", new JsonPrimitive(jockey));
		result.add("trainer", new JsonPrimitive(trainer));

		if (this.pronostic != null)
			this.pronostic.addToSerialization(result);

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

	public void setPronostic(Pronostic p) {
		this.pronostic = p;
	}

	/**
	 * public void enrichWithDetail(Pronostic p) { if
	 * (p.getClass().getSimpleName().equals("PronosticModelOne")) {
	 * PronosticModelOne p1 = (PronosticModelOne) p; this.enrichWithDetail(p1);
	 * 
	 * } else if (p.getClass().getSimpleName().equals("PronosticModelTwo")) {
	 * PronosticModelTwo p2 = (PronosticModelTwo) p; this.enrichWithDetail(p2);
	 * } }
	 * 
	 * public void enrichWithDetail(PronosticModelOne p) { p.horseName }
	 * 
	 * public void enrichWithDetail(PronosticModelTwo p) {
	 * 
	 * }
	 */

}
