package springrunner;


/**
 * Define operations that will expose the {@link JmxSpringServiceRunner} to JMX.
 * 
 * @see JmxSpringServiceRunner
 * 
 * @author Zemian Deng
 */
public interface SpringRunnerMBean extends Service {
	
	public void shutdown();

	public String listAppCtxBeans();

	public String inspectAppCtx();
}
