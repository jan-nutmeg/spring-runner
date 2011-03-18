package org.springrunner.service;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.commons.lang.ArrayUtils;
import org.springrunner.service.spring.SpringService;


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
public class SpringRunner extends ServiceRunner implements SpringRunnerMBean {
	
	public static final String DEFAULT_JMX_NAME = "org.springrunner:name=SpringServiceRunner";
	private boolean noJmx;
	private String jmxName;
		
	public SpringService getSpringService() {
		return (SpringService)service;
	}

	@Override
	public void run() {
		if (!noJmx) {
			registerThisJmxRemoteMBean();
		}
		super.run();
	}
	
	@Override
	public void shutdown() {
		if (!noJmx) {
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

	public static void main(String[] args) {
		// Process helppage
		if (args.length < 1 || args[0].equals("-h")) {
			System.out.println(
				"Usage java [options] SpringRunner <springXmlFile ...>\n" +
				"\n" +
				"[options]\n" +
				"-DwaitAndNotify=false Do not wait and block after start service. Default true.\n" +
				"-DnoJmx=false         Do not register runner as MBean. Default true.\n" +
				"-DjmxName=<name>      Change the name used to register runner as MBean.\n" +
				"                       The default is org.springrunner:name=SpringServiceRunner\n" +
				"\n");
			return;
		}
		
		// Build new args for this SpringRunner, insert service class name for parent runner use.
		String[] newArgs = (String[])ArrayUtils.add(args, 0, SpringService.class.getName());
		
		// Create, init, and run the main runner
		SpringRunner main = new SpringRunner();
		main.arguments = newArgs;
		main.waitAndNotify = Boolean.valueOf(System.getProperty("waitAndNotify", "true"));
		main.noJmx = Boolean.valueOf(System.getProperty("noJmx", "false"));
		main.jmxName = System.getProperty("jmxName", DEFAULT_JMX_NAME);
		
		main.init();
		
		main.run();
	}
}
