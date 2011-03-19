package springrunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple task that just log a message during run.
 * 
 * @author Zemian Deng
 */
public class SimpleTask implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(SimpleTask.class);

	@Override
	public void run() {
		logger.info("Task " + this + " is running.");
	}
}
