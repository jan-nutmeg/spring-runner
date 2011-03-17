package org.springrunner.tool;

import java.net.URL;
import java.util.Enumeration;

/**
 * Print which jar or classpath of a resource name comes from.
 * 
 * @author Zemian Deng
 */
public class WhichResource {
	public static void main(String[] args) throws Exception {
		if (args.length < 1)
			throw new RuntimeException("Usage: java WhichResource <name>");
		
		String name = args[0];
		
		boolean found = false;
		Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(name);
		while (resources.hasMoreElements()) {
			URL url = resources.nextElement();
			System.out.println(url);
			found = true;
		}
		
		if (!found) {
			// try it as class name
			try {
				Class<?> cls = Class.forName(name);
				URL url = cls.getProtectionDomain().getCodeSource().getLocation();
				System.out.println(url);
				found = true;
			} catch (Exception e) {
				// do nothing.
			}
		}
		
		if (!found)
			System.out.println("No resource nor classname found: " + name);
	}
}
