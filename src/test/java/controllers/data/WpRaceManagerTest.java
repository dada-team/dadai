package test.java.controllers.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Test;

import main.java.controllers.data.TurfoWpRaceParser;
import main.java.controllers.data.WpRaceManager;
import main.java.controllers.interfaces.WpParser;
import main.java.model.impl.Finish;
import main.java.model.impl.FinishList;
import main.java.model.impl.WpHorseTurfo;
import main.java.model.impl.WpRaceTurfo;
import main.java.model.interfaces.WebPage;

public class WpRaceManagerTest {
	Logger  logger = Logger.getLogger("test.java.controllers.data.WpRaceManagerTest");
	
	@Test
	public void testBasicExecution() throws IOException {
		File f = new File("src/test/resources");
		logger.debug(f.getAbsolutePath());
		WpRaceManager myUnit = new WpRaceManager(f);
		FinishList fl = new FinishList();
		List<Finish> finish = new ArrayList<Finish>();
		List<String> lastPerf = new ArrayList<String>();
		List<WebPage> webPages = new ArrayList<WebPage>();
		lastPerf.add("1p");
		lastPerf.add("2p");
		WpHorseTurfo wph = new WpHorseTurfo(
				new URL("http://www.turfomania.fr/fiches/chevaux/oathkeeper.html?idcheval=773135"), 773135,
				"oathkeeper", "3", "Hongre", lastPerf);

		WpHorseTurfo wph2 = new WpHorseTurfo(
				new URL("http://www.turfomania.fr/fiches/chevaux/oathkeeper.html?idcheval=773135"), 773136,
				"circumference", "3", "Mâle", lastPerf);

		Finish f1 = new Finish(1, (float) 2.55, "mr uck", wph);
		Finish f2 = new Finish(2, (float) 3.15, "mr orange", wph2);
		finish.add(f1);
		finish.add(f2);
		fl.setFinishes(finish);
		String raceDescription = "GOOD COURSE COOL STORY";

		WpRaceTurfo tfWr = new WpRaceTurfo(
				new URL("http://www.turfomania.fr/pronostics/rapports-dimanche-12-juillet-2015-chantilly-prix-de-l-hermitage.html?idcourse=191143"),
				191143, "chantilly-prix-de-l-hermitage", new DateTime(), raceDescription, fl);

		webPages.add(tfWr);
		myUnit.setWebPages(webPages);

		File fTestPrevious = new File(f, tfWr.getFileName());
		fTestPrevious.delete();
		
		myUnit.writeToJSON();

		// test generation fichier
		File fTest = new File(f, tfWr.getFileName());
		logger.debug("absolute path : " + fTest.getAbsolutePath());

		if (fTest.exists()) {
			//fTest.delete();
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

}
