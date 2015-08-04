package test.java.controllers.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.joda.time.DateTime;
import org.jsoup.select.Elements;
import org.junit.Test;

import main.java.controllers.data.TurfoWpRaceParser;
import main.java.controllers.data.WpRaceParameters;
import main.java.controllers.interfaces.WpParser;
import main.java.controllers.sniffers.TurfoRaceSniffer;
import main.java.model.impl.WpRaceTurfo;

public class TurfoWpRaceParserTest {

	@Test
	public void testBasicExecution() throws Exception {
		WpParser myUnit = new TurfoWpRaceParser(false);

		URL testUrl = new URL(
				"http://www.turfomania.fr/pronostics/rapports-dimanche-12-juillet-2015-chantilly-prix-de-l-hermitage.html?idcourse=191143");
		myUnit.parse(testUrl);

		assertEquals(0, 0);
	}

	@Test
	public void testUrlParsing() throws IOException {
		TurfoWpRaceParser myUnit = new TurfoWpRaceParser(false);

		URL testUrl = new URL(
				"http://www.turfomania.fr/pronostics/rapports-dimanche-12-juillet-2015-chantilly-prix-de-l-hermitage.html?idcourse=191143");

		WpRaceTurfo wp = myUnit.initWebPage(testUrl);
		assertTrue(wp.getFileName() != null);
		assertTrue(wp.getDtEvent() != null);
	}
	
}
