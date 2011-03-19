package springrunner.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Print all Java System Properties by sorted keys.
 *
 * @author Zemian Deng
 */
public class SysProps {
	
	public static void main(String[] args) {
		List<String> keys = new ArrayList<String>();
		for (String name : System.getProperties().stringPropertyNames())
			keys.add(name);
		Collections.sort(keys);
		for (String name : keys)
			System.out.println(name + " => " + System.getProperty(name));
	}
	
}