package org.springrunner.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrunner.service.spring.SpringServiceRunner;

/**
 * Invoke any operation on a jmx remote mbean.
 * 
 * <p>
 * User may use this tool to shutdown a {@link SpringServiceRunner} instance.
 * 
 * <p>
 * Usage: java InvokeMBean <operation> [mbeanObjectName] [serviceUrl]
 * 
 * <p>
 * Example: 
 * <pre>
 * java InvokeMBean shutdown org.springrunner:name=JmxRemoteSpringServiceRunner service:jmx:rmi:///jndi/rmi://localhost:12345/jmxrmi
 * 
 * java InvokeMBean :list-ops org.springrunner:name=JmxRemoteSpringServiceRunner service:jmx:rmi:///jndi/rmi://localhost:12345/jmxrmi
 * 
 * java InvokeMBean :list-mbeans *:* service:jmx:rmi:///jndi/rmi://localhost:12345/jmxrmi
 * 
 * java InvokeMBean :list-mbeans *:* service:jmx:rmi:///jndi/rmi://localhost:12345/jmxrmi -Djmx.user=<name> -Djmx.passwd=<passwd>
 * </pre>
 * 
 * @see SpringServiceRunner
 * 
 * @author Zemian Deng
 */
public class InvokeMBean {
	
	private static Logger logger = LoggerFactory.getLogger(InvokeMBean.class);
	
	private String[] args;

	public InvokeMBean(String[] args) {
		this.args = args;
	}

	private void run() {
		String user = System.getProperty("jmx.user", "");
		String passwd = System.getProperty("jmx.passwd", "");

		String service = "service:jmx:rmi:///jndi/rmi://localhost:12345/jmxrmi";
		String objectName = "org.springrunner:name=JmxRemoteSpringServiceRunner";
		String operation = args[0];
		if (args.length >= 2)
			objectName = args[1];
		if (args.length >= 3)
			service = args[2];

		try {
			MBeanServerConnection mbeanServer = connect(service, user, passwd);
			if (operation.equals(":list-ops")) {
				listOperations(mbeanServer, objectName);
			} else if (operation.equals(":list-mbeans")) {
				listMBeans(mbeanServer);
			} else {
				invokeOperation(mbeanServer, objectName, operation);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed", e);
		}
		logger.info("Done.");
	}

	private void listMBeans(MBeanServerConnection mbeanServer) throws Exception {
		logger.debug("List of all mbeans for remote mbeanServer.");
		ObjectName name = null;
		QueryExp query = null;
		Set<ObjectName> names = mbeanServer.queryNames(name, query);
		List<String> sortedNames = new ArrayList<String>();
		for (ObjectName objName : names)
			sortedNames.add(objName.getCanonicalName());
		Collections.sort(sortedNames);
		for (String mbeanName : sortedNames)
			logger.info("  " + mbeanName);
	}

	private void listOperations(MBeanServerConnection mbeanServer, String objectName) throws Exception {
		logger.info("List of operations for mbean: " + objectName);
		ObjectName name = new ObjectName(objectName);
		MBeanInfo mbeanInfo = mbeanServer.getMBeanInfo(name);
		for (MBeanOperationInfo opInfo : mbeanInfo.getOperations()) {
			String msg = opInfo.getName() + ", returnType=" + opInfo.getReturnType() + ", signature=" + Arrays.asList(opInfo.getSignature());
			logger.info("  " + msg);
		}
	}

	private MBeanServerConnection connect(String service, String user, String passwd) throws Exception {
		logger.debug("Connection to mbeanServer: " + service);
		Map<String, Object> environment = new HashMap<String, Object>();
		if (!user.equals("")) {
			logger.debug("Setting connection env with user authenticatio: " + user);
			environment.put(JMXConnector.CREDENTIALS, new String[] { user, passwd });
		}
		JMXServiceURL url = new JMXServiceURL(service);
		JMXConnector conn = JMXConnectorFactory.connect(url, environment);
		MBeanServerConnection mbeanServer = conn.getMBeanServerConnection();
		return mbeanServer;
	}

	private void invokeOperation(MBeanServerConnection mbeanServer, String objectName, String operation) throws Exception {
		ObjectName name = new ObjectName(objectName);
		logger.debug("Invoking " + operation + " on mbean " + objectName);
		Object ret = mbeanServer.invoke(name, operation, null, null);
		if (ret != null) {
			logger.info("== Result == ");
			logger.info(ret.toString());
		}
	}

	public static void main(String[] args) {
		new InvokeMBean(args).run();
	}

}
