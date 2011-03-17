package org.springrunner.service.spring;

import java.lang.management.ManagementFactory;
import java.util.Arrays;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrunner.service.ServiceRunner;


/**
 * This is a convenient class that extend to ServiceRunner to always create SpringService.
 * 
 * <p>
 * These two examples are equivalent: 
 * <pre>
 * java ServiceRunner org.springrunner.service.springSpringService [spring-beans.xml ...]
 * 
 * java SpringServiceRunner [spring-beans.xml ...]
 * </pre>
 * 
 * @see SpringService
 * 
 * @author Zemian Deng
 */
public class SpringServiceRunner extends ServiceRunner implements SpringServiceRunnerMBean {
	
	public static final String DEFAULT_JMX_NAME = "org.springrunner:name=SpringServiceRunner";

	private String jmxName;

	public SpringServiceRunner(String[] args) {
		super(createRunnerArgs(args));
		
		// Allow user to override the jmx remote name using system props.
		jmxName = System.getProperty("jmxName", DEFAULT_JMX_NAME);
		setName(jmxName); // set this service name to this as well for nice logging.
	}
	
	private static String[] createRunnerArgs(String[] args) {
		String[] runnerArgs = new String[args.length + 1];
		runnerArgs[0] = SpringService.class.getName();
		System.arraycopy(args, 0, runnerArgs, 1, args.length);
		return runnerArgs;
	}

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(SpringServiceRunner.class);
		logger.debug("Main args: " + Arrays.asList(args));
		
		SpringServiceRunner main = new SpringServiceRunner(args);
		main.run();
	}
	
	public SpringService getSpringService() {
		return (SpringService)service;
	}

	@Override
	public void run() {
		if (waitAndNotify) {
			registerThisJmxRemoteMBean();
		}
		super.run();
	}
	
	@Override
	public void shutdown() {
		if (waitAndNotify) {
			unregisterThisJmxRemoteMBean();
		}
		notifyShutdownEvent();
	}
	
	@Override
	public String inspectAppCtx() {
		return getSpringService().inspectAppCtx();
	}

	@Override
	public String listAppCtxBeans() {
		return getSpringService().listAppCtxBeans();
	}

	protected void registerThisJmxRemoteMBean() {
		logger.debug("Registering PlatformMBeanServer MBean: " + jmxName + " for instance: " + this);
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		try {
			ObjectName objectName = new ObjectName(jmxName);
			server.registerMBean(this, objectName);
			logger.info("Registered MBean: " + objectName);
		} catch (Exception e) {
			throw new RuntimeException("Failed to register mbean: " + jmxName, e);
		}
	}

	protected void unregisterThisJmxRemoteMBean() {
		logger.debug("Unregistering PlatformMBeanServer MBean: " + jmxName + " for instance: " + this);
		MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		try {
			ObjectName objectName = new ObjectName(jmxName);
			server.unregisterMBean(objectName);
			logger.info("Unregistered MBean: " + objectName);
		} catch (Exception e) {
			throw new RuntimeException("Failed to unregister mbean: " + jmxName, e);
		}
	}
}
