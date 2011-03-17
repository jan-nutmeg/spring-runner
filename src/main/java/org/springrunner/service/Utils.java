package org.springrunner.service;

import java.io.FileReader;
import java.util.Properties;

/**
 * Utilities.
 * 
 * @author Zemian Deng
 */
public class Utils {
	
	public static Properties loadProperties(String name) {
		Properties props = new Properties();
		try {
			props.load(new FileReader(name));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return props;
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException("Failed to sleep " + millis + " millis.", e);
		}
	}
	
}
