package org.springrunner.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springrunner.service.AbstractService;
import org.springrunner.service.ArgumentsSetter;
import org.springrunner.service.ServiceRunner;


/**
 * A unit test for {@link ServiceRunner}.
 *
 * @author Zemian Deng
 */
public class ServiceRunnerTest {
	
	@Test
	public void testRunWithoutWait() throws Exception {
		StartCounterService service = new StartCounterService();
		ServiceRunner runner = new ServiceRunner();
		runner.setService(service);
		runner.setWaitAndNotify(false);
		
		assertThat(runner.isStarted(), is(false));
		assertThat(runner.isRunning(), is(false));
		assertThat(service.isStarted(), is(false));
		assertThat(service.startCount, is(0));
		assertThat(service.stopCount, is(0));
		
		runner.run();
		
		assertThat(runner.isStarted(), is(false));
		assertThat(runner.isRunning(), is(false));
		assertThat(service.isStarted(), is(false));
		assertThat(service.startCount, is(1));
		assertThat(service.stopCount, is(1));
	}
	
	@Test
	public void testRunAndWait() throws Exception {
		StartCounterService service = new StartCounterService();
		final ServiceRunner runner = new ServiceRunner();
		runner.setService(service);
		runner.setWaitAndNotify(true);
		
		assertThat(runner.isStarted(), is(false));
		assertThat(runner.isRunning(), is(false));
		assertThat(service.isStarted(), is(false));
		assertThat(service.startCount, is(0));
		assertThat(service.stopCount, is(0));
		
		// Run
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				runner.run();
			}
		});
		t.start();
	
		// Wait for runner to start
		long maxWaitTimeMillis = 2000;
		long startTimeMillis = System.currentTimeMillis();
		while(!runner.isStarted() && (System.currentTimeMillis() - startTimeMillis) < maxWaitTimeMillis)
			Thread.sleep(500);
		assertThat(runner.isStarted(), is(true));

		// After runner is started, we check these
		assertThat(runner.isRunning(), is(true));
		assertThat(service.isStarted(), is(true));
		assertThat(service.startCount, is(1));
		assertThat(service.stopCount, is(0));
		
		// Let's try pausing the runner+service
		runner.stop();
		assertThat(runner.isRunning(), is(true));
		assertThat(service.isStarted(), is(false));
		assertThat(service.startCount, is(1));
		assertThat(service.stopCount, is(1));

		// Let's try resume the runner+service
		runner.start();
		assertThat(runner.isRunning(), is(true));
		assertThat(service.isStarted(), is(true));
		assertThat(service.startCount, is(2));
		assertThat(service.stopCount, is(1));
		
		
		// Prepare to shutdown
		runner.shutdownRun();
		maxWaitTimeMillis = 2000;
		startTimeMillis = System.currentTimeMillis();
		while(runner.isStarted() && (System.currentTimeMillis() - startTimeMillis) < maxWaitTimeMillis)
			Thread.sleep(500);
		assertThat(runner.isStarted(), is(false));

		// After runner is stopped, we check these
		assertThat(runner.isRunning(), is(false));
		assertThat(service.isStarted(), is(false));
		assertThat(service.startCount, is(2));
		assertThat(service.stopCount, is(2));
	}
	
	@Test
	public void testMainWithoutWait() throws Exception {
		System.setProperty("waitAndNotify", "false");
		
		resetStartCounters();
		String[] args = new String[] { ExternalStartCounterService.class.getName() };
		ServiceRunner.main(args);

		assertThat(ServiceRunnerTest.startCount, is(1));
		assertThat(ServiceRunnerTest.stopCount, is(1));
		assertThat(ServiceRunnerTest.serviceArgs, nullValue());
		
		resetStartCounters();
		args = new String[] { ExternalStartCounterServiceWithArgs.class.getName(), "a", "b", "c" };
		ServiceRunner.main(args);

		assertThat(ServiceRunnerTest.startCount, is(1));
		assertThat(ServiceRunnerTest.stopCount, is(1));
		assertThat(ServiceRunnerTest.serviceArgs, is(new String[] {"a", "b", "c"}));

		System.getProperties().remove("waitAndNotify");
		
	}
	
	private static int startCount = 0;
	private static int stopCount = 0;
	private static String[] serviceArgs;
	private void resetStartCounters() {
		startCount = stopCount = 0;
		serviceArgs = null;
	}
	
	public static class ExternalStartCounterService extends AbstractService {

		@Override
		protected void startService() {
			ServiceRunnerTest.startCount ++;
		}

		@Override
		protected void stopService() {
			ServiceRunnerTest.stopCount ++;
		}
	}
	
	public static class ExternalStartCounterServiceWithArgs extends AbstractService implements ArgumentsSetter {

		@Override
		public void setArguments(String[] args) {
			ServiceRunnerTest.serviceArgs = args;
		}		

		@Override
		protected void startService() {
			ServiceRunnerTest.startCount ++;
		}

		@Override
		protected void stopService() {
			ServiceRunnerTest.stopCount ++;
		}
	}
}
