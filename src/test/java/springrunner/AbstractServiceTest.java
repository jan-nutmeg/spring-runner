package springrunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.test.util.ReflectionTestUtils;

import springrunner.AbstractService;


/**
 * A unit test for {@link AbstractService}. We want to ensure the isStarted()
 * is set consistently for subclass to use.
 *
 * @author Zemian Deng
 */
public class AbstractServiceTest {
	
	@Test
	public void testInheritableLogger() throws Exception {
		// a logger from AbstractService should be inheritable and set to the subclass name.
		StartCounterService service = new StartCounterService();
		Logger logger = (Logger)ReflectionTestUtils.getField(service, "logger");
		assertThat(logger.getName(), not(AbstractService.class.getName()));
		assertThat(logger.getName(), is(StartCounterService.class.getName()));
	}
	
	@Test
	public void testName() throws Exception {
		StartCounterService service = new StartCounterService();
		// default service name is <className>@<identityHash>
		assertThat(service.getName(), is(StartCounterService.class.getName() + "@" + System.identityHashCode(service)));
		
		service = new StartCounterService("testService");
		assertThat(service.getName(), is("testService"));
	}
	
	@Test
	public void testStartStopService() throws Exception {
		StartCounterService service = new StartCounterService();
		assertThat(service.isStarted(), is(false));
		assertThat(service.startCount, is(0));
		assertThat(service.stopCount, is(0));
		
		service.start();
		assertThat(service.isStarted(), is(true));
		assertThat(service.startCount, is(1));
		assertThat(service.stopCount, is(0));
		
		// start again while it's started.
		service.start();
		assertThat(service.isStarted(), is(true));
		assertThat(service.startCount, is(1));
		assertThat(service.stopCount, is(0));

		service.stop();
		assertThat(service.isStarted(), is(false));
		assertThat(service.startCount, is(1));
		assertThat(service.stopCount, is(1));
		
		// stop again while it's stopped.
		service.stop();
		assertThat(service.isStarted(), is(false));
		assertThat(service.startCount, is(1));
		assertThat(service.stopCount, is(1));
		
		// start normal again
		service.start();
		assertThat(service.isStarted(), is(true));
		assertThat(service.startCount, is(2));
		assertThat(service.stopCount, is(1));
	}
}
