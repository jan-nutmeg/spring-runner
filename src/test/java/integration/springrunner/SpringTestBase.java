package integration.springrunner;

import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springrunner.TestBase;


/**
 * Base class to let subclass inherit a Spring appCtx, which is provided by Spring's TestContext framwork.
 * 
 * Subclass needs to add
 *   import org.springframework.test.context.ContextConfiguration;
 *   
 *   @ContextConfiguration(locations="classpath:myapp-test-beans.xml")
 * 
 * @author Zemian Deng
 */
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class SpringTestBase extends TestBase implements ApplicationContextAware
{
	protected ApplicationContext appCtx;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		appCtx = applicationContext;
	}

	public void listBeanNames()
	{
		String[] names = appCtx.getBeanDefinitionNames();
		for (String name : names)
			logger.info(name);
	}
}
