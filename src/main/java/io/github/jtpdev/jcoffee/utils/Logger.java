package io.github.jtpdev.jcoffee.utils;

import java.util.List;

/**
 * @author Jimmy Porto
 *
 */
public class Logger {

	public static Boolean doLog = true;
	private static Long[] times = new Long[2];

	/**
	 * This methods print in console the log that the developer want to print.
	 * 
	 * @param values
	 */
	public static void show(List<Object> values) {
		if (doLog) {
			for (Object value : values) {
				System.err.println("Log_" + System.currentTimeMillis() + ".:" + value);
			}
		}
	}
	
	/**
	 * This methods print in console the log that the developer want to print.
	 * 
	 * @param values
	 */
	public static void show(LoggerMessage... values) {
		if (doLog) {
			for (LoggerMessage value : values) {
				System.err.println("Log_" + System.currentTimeMillis() + ".:" + value);
			}
		}
	}

	/**
	 * This methods print in console the log that the developer want to print.
	 * 
	 * @param values
	 */
	public static void show(String... values) {
		if (doLog) {
			for (String value : values) {
				System.err.println("Log_" + System.currentTimeMillis() + ".:" + value);
			}
		}
	}

	/**
	 * This method add a time of now.
	 * If the times already have two values, the method will override the next position (0,1) like a cycle.
	 * 
	 * @param timeInMillis
	 */
	public static void time() {
		time(System.currentTimeMillis());
	}
	
	/**
	 * This method add a time that the user want to save.
	 * If the times already have two values, the method will override the next position (0,1) like a cycle.
	 * 
	 * @param timeInMillis
	 */
	public static void time(Long timeInMillis) {
		if(times[0] != null) {
			if(times[1] != null) {
				times[0] = times[1];
			}
			times[1] = timeInMillis;
		} else {
			times[0] = timeInMillis;
		}
	}

	/**
	 * This method print in console the time spent over the times added to be calculate.
	 */
	public static void showTime() {
		show(createTimeLog());
	}
	
	/**
	 * This method print in console the time spent over the times added to be calculate.
	 * And add a message to show with.
	 */
	public static void showTime(String message) {
		show(message + " - " + createTimeLog());
	}
	
	/**
	 * This method creates the timeLog for the showTime methods.
	 * 
	 * @return
	 */
	private static String createTimeLog() {
		String log = "No time to show";
		if (times[1] == null && times[0] != null || times[1] != null) {
			time();
			log = "Time spent: " + (times[1] - times[0]) + " milliseconds.";
		}
		return log;
	}

}
