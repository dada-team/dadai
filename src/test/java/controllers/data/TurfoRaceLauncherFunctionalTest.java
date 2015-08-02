package test.java.controllers.data;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import main.java.controllers.launchers.TurfoRaceLauncher;

public class TurfoRaceLauncherFunctionalTest {
	Logger  logger = Logger.getLogger("test.java.controllers.launchers.TurfoRaceLauncherFunctionalTest");
	DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");

	@Test
	public void basicExecution(){
		File f = new File("src/test/resources");
        DateTime dtStart = formatter.parseDateTime("01/07/2015");
        DateTime dtEnd = formatter.parseDateTime("01/07/2015");
		TurfoRaceLauncher tfl = new TurfoRaceLauncher(f, dtStart, dtEnd);
		tfl.loadAllData();
		try {
			tfl.writeAllData();
			assertTrue(true);
		} catch (Exception e) {
			logger.error(e);
			assertTrue(false);
		}
	}
}
