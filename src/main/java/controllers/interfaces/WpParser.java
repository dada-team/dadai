package main.java.controllers.interfaces;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.jsoup.nodes.Document;

import main.java.model.interfaces.WebPage;

public interface WpParser {
	public abstract WebPage parse(URL url) throws IOException;
}
