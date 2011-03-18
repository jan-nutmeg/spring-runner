package org.springrunner.service.spring;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springrunner.service.AbstractService;
import org.springrunner.service.ArgumentsListener;
import org.springrunner.service.SpringRunner;


/**
 * A service that will start and stop a spring context with zero or more xml
 * configuration files. (zero files will starts a context without any beans!)
 * 
 * <p>
 * Default xml config are files in relative to where the Java process is started. User
 * may use an URI format prefix on any of the config file (eg: file:, http: etc.) There
 * is also a custom prefix classpath: that can load as resource on Java's classpath.
 * 
 * @see SpringRunner
 * 
 * @author Zemian Deng
 */
public class SpringService extends AbstractService implements ArgumentsListener {
	
	private ConfigurableApplicationContext appCtx;
	private String[] xmlFiles;

	public SpringService() {
	}
	
	public SpringService(String... xmlFiles) {
		this.xmlFiles = xmlFiles;
	}

	@Override
	public void onArguments(String[] args) {
		xmlFiles = args;
	}

	@Override
	public void startService() {
		String debugMsg = (xmlFiles == null ? "null" : Arrays.asList(xmlFiles).toString());
		logger.debug("Starting Spring context with xmlFiles: " + debugMsg);
		appCtx = new FileSystemXmlApplicationContext(xmlFiles);
	}

	@Override
	public void stopService() {
		logger.debug("Stopping Spring context: " + appCtx);
		appCtx.close();
	}

	// == Diagnostic Methods ==
	public String listAppCtxBeans() {
		StringBuilder sb = new StringBuilder();
		Map<?, ?> beans = appCtx.getBeansOfType(null); // get all concrete beans
		for (Object key : beans.keySet()) {
			sb.append("beanId=" + key + ", class=" + beans.get(key).getClass().getName() + "\n");
		}
		sb.append("beansCount=" + beans.size() + "\n");
		String beansStr = sb.toString();
		logger.debug("appCtx Beans:\n" + beansStr);
		return beansStr;
	}

	public String inspectAppCtx() {
		boolean outputTransients = false;
		Class<?> reflectUpToClass = Object.class;
		String appCtxStatus = ToStringBuilder.reflectionToString(appCtx, ToStringStyle.MULTI_LINE_STYLE, outputTransients, reflectUpToClass);
		logger.debug("appCtx status:\n" + appCtxStatus);
		return appCtxStatus;
	}
}
