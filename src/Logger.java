import java.lang.*;
import java.util.*;
import java.text.*;

public final class Logger {
	private Logger() {}

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public static String padRight(String string, int width, char padChar) {
		return (padChar + "").repeat(Math.max(0, width - string.length())) + string;
	}

	public static String padRight(String string, int width) {
		return padRight(string, width, ' ');
	}

	private static void log(String type, String format, Object... args) {
		System.err.printf(DATE_FORMAT.format(new Date()) + " " + type + "  " + format + "\n", args);
	}

	public static void debug(String format, Object... args) {
		log("\033[1;32mDEBUG\033[0m", format, args);
	}

	public static void info(String format, Object... args) {
		log("\033[1;34mINFO \033[0m", format, args);
	}

	public static void warn(String format, Object... args) {
		log("\033[1;33mWARN \033[0m", format, args);
	}

	public static void error(String format, Object... args) {
		log("\033[1;31mERROR\033[0m", format, args);
	}
}
