package main.java.controllers.launchers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;

import main.java.model.interfaces.WebPage;

public class CommandParser {
	/**
	 * Main method : launch at first
	 * 
	 * @param args
	 *            command line arguments
	 */
	static Logger logger = Logger.getLogger("main.java.controllers.launchers.CommandParser");

	public static Options getOptions() {
		Options options = new Options();
		options.addOption("help", "h", false, "help");
		options.addOption("dtstart", "ds", true, "");
		options.addOption("dtend", "ds", true, "");
		options.addOption("o", "o", true, "json output dir");

		return options;
	}

	/**
	 * 
	 * @param cmd
	 */
	private static void process(CommandLine cmd) {
		if (cmd.hasOption("help") || (!cmd.hasOption("dtstart") || (!cmd.hasOption("dtend")))
				|| (!cmd.hasOption("o"))) {
			HelpFormatter formatter = new HelpFormatter();
			String programName = new java.io.File(
					CommandParser.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
			formatter.printHelp(programName, getOptions());
		} else {
			DateTime dtStart = new DateTime(cmd.getOptionValue("dtstart"));
			DateTime dtEnd = new DateTime(cmd.getOptionValue("dtend"));
			File output = new File(cmd.getOptionValue("o"));
			try {
				processLaunch(output, dtStart, dtEnd);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.debug("ERROR during the main program");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param dtStart
	 * @param dtEnd
	 * @throws IOException 
	 */
	private static void processLaunch(File output, DateTime dtStart, DateTime dtEnd) throws IOException {
		TurfoRaceLauncher trl = new TurfoRaceLauncher(output, dtStart, dtEnd);
		trl.loadAllData();
		trl.writeAllData();
	}

	/**
	 * 
	 */
	private static void processLaunchMultiThreaded(File output, DateTime dtStart, DateTime dtEnd)
			throws InterruptedException, ExecutionException {

		int availableProcessors = Runtime.getRuntime().availableProcessors();

		if ((Days.daysBetween(dtStart, dtEnd).getDays() > 600) && (availableProcessors > 4)) {
			processLaunchMultiThreaded(output, dtStart, dtEnd, 4);
		} else if ((Days.daysBetween(dtStart, dtEnd).getDays() > 400) && (availableProcessors > 3)) {
			processLaunchMultiThreaded(output, dtStart, dtEnd, 3);
		} else if ((Days.daysBetween(dtStart, dtEnd).getDays() > 200) && (availableProcessors > 2)) {
			processLaunchMultiThreaded(output, dtStart, dtEnd, 2);
		} else {
			processLaunchMultiThreaded(output, dtStart, dtEnd, 1);
		}

		return;
	}

	/**
	 * 
	 * @param dtStart
	 * @param dtEnd
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	private static void processLaunchMultiThreaded(File output, DateTime dtStart, DateTime dtEnd, int nbOfThreads)
			throws InterruptedException, ExecutionException {
		ExecutorService service = Executors.newFixedThreadPool(nbOfThreads);
		List<Future<List<WebPage>>> futures = new ArrayList<Future<List<WebPage>>>();

		/**
		 * 1 get data
		 */
		for (int i = 0; i < nbOfThreads; i++) {
			futures.add(service.submit(new TurfoRaceLauncher(output, dtStart, dtEnd)));
		}

		List<WebPage> webPages = new ArrayList<WebPage>();

		for (Future<List<WebPage>> future : futures) {
			webPages.addAll(extract(future));
		}

		/**
		 * 2 write data
		 */
		//write all webpages
		logger.debug("TODO");

		return;
	}

	private static List<WebPage> extract(Future<List<WebPage>> future) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		List<WebPage> webPages = null;
		try {
			webPages = future.get();
		} catch (InterruptedException e) {
			logger.error(e);
			// TODO Auto-generated catch block
			throw e;
		} catch (ExecutionException e) {
			logger.error(e);
			// TODO Auto-generated catch block
			throw e;
		}

		return webPages;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Options options = getOptions();
		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine cmd = parser.parse(options, args);
			process(cmd);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}