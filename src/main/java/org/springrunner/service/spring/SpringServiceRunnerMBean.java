package org.springrunner.service.spring;

import org.springrunner.service.Service;

/**
 * Define operations that will expose the {@link JmxSpringServiceRunner} to JMX.
 * 
 * @see JmxSpringServiceRunner
 * 
 * @author Zemian Deng
 */
public interface SpringServiceRunnerMBean extends Service {
	
	public void shutdown();

	public String listAppCtxBeans();

	public String inspectAppCtx();
}
