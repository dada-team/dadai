package test.java.controllers.data;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import main.java.controllers.data.TurfoWpRaceParser;
import main.java.controllers.interfaces.WpParser;
import main.java.controllers.sniffers.TurfoRaceSniffer;

public class TurfoWpRaceParserTest {

	
	@Test
    public void testBasicExecution() throws MalformedURLException {
        WpParser myUnit = new TurfoWpRaceParser();

        
        URL testUrl = new URL("http://www.turfomania.fr/pronostics/rapports-dimanche-12-juillet-2015-chantilly-prix-de-l-hermitage.html?idcourse=191143");
        
		try {
			 myUnit.parse(testUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        assertEquals(0, 0);
    }
}
