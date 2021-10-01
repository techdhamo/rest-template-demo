package in.otomate.adminloginservice.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

	private Log() {
		throw new IllegalStateException("Constructor Not Allowed");
	}

	private static final Logger logger = LogManager.getLogger();

	public static void info(Object obj, String message) {
		logger.info(obj.getClass().getName() + " - " + message);
	}

	public static void error(Object obj, String message) {
		logger.error(obj.getClass().getName() + " - " + message);
	}

	public static void warning(Object obj, String message) {
		logger.warn(obj.getClass().getName() + " - " + message);
	}

	public static void trace(Object obj, String message) {
		logger.trace(obj.getClass().getName() + " - " + message);
	}

	public static void debug(Object obj, String message) {
		logger.debug(obj.getClass().getName() + " - " + message);
	}

	public static void fatal(Object obj, String message) {
		logger.fatal(obj.getClass().getName() + " - " + message);
	}
}
