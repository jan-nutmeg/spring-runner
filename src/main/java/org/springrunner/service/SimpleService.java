package org.springrunner.service;


/**
 * A simple service that just log a message during start and stop states.
 * 
 * @author Zemian Deng
 */
public class SimpleService extends AbstractService {
	
	@Override
	public void startService() {
		logger.debug("Starting " + name);
	}

	@Override
	public void stopService() {
		logger.debug("Stoping " + name);
	}
}
