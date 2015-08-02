package main.java.controllers.data;

public class WpRaceParameters {

	protected static final String URL_RACE_PATTERN_REGEXP = ".*/rapports-[^-]+-([^-]+-[^-]+-[^-]+)-(.+)\\.html\\?idcourse=([0-9]+)";
	protected static final String HORSE_PATTERN_SELECT = "table.tableauLine tbody tr td:eq(2) a[href]";
	protected static final String HORSE_COTES_SELECT = "table.tableauLine tbody tr td:eq(6)";
}
