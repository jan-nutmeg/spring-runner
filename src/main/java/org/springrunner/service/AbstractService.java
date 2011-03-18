package org.springrunner.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A base service that provides an instance logger, a name, and a {@link #started} flag
 * for subclass. The subclass should use the {@link #startService()} and {@link #stopService()} 
 * methods.
 * 
 * @author Zemian Deng
 */
public abstract class AbstractService implements Service {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected String name;
	protected volatile boolean started;
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public boolean isStarted() {
		return started;
	}

	public AbstractService() {
		name = getClass().getName() + "@" + System.identityHashCode(this);
	}
	
	@Override
	public void start() {
		if (started) {
			logger.debug("No action. " + name + " has already started.");
			return;
		}
		
		started = true;		
		startService();
	}
	
	@Override
	public void stop() {
		if (!started) {
			logger.debug("No action. " + name + " has not started yet.");
			return;
		}
		
		started = false;
		stopService();
	}
	
	@Override
	public String toString() {
		return "Service[" + name + "]";
	}
	
	abstract protected void startService();
	abstract protected void stopService();
	
}
