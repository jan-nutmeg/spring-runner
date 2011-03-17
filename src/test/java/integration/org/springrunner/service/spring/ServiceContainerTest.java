package integration.org.springrunner.service.spring;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springrunner.service.Service;
import org.springrunner.service.ServiceContainer;
import org.springrunner.service.StartCounterService;

/**
 * ServiceContainerTest is an integration test for the {@link ServiceContainer}
 * configured with some {@link StartCounterService}. We want to ensure when container
 * starts or stops, all the services in it will also propagated.
 *
 * We will use Spring integration technique with their annotation to inject test
 * beans for dependencies.
 *
 * @author Zemian Deng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/deng/integration/service/spring/servicecontainer-test-beans.xml")
public class ServiceContainerTest {
	
	// == These resources will be injected by Spring ==
	@Resource(name="serviceContainerWithoutAutoStart")
	private ServiceContainer serviceContainerWithoutAutoStart;

	@Resource(name="serviceContainerWithAutoStart")
	private ServiceContainer serviceContainerWithAutoStart;
	
	// == tests ==
	@Test
	public void testServiceContainerWithoutAutoStart() throws Exception {
		assertThat(serviceContainerWithoutAutoStart.isStarted(), is(false));

		@SuppressWarnings("unchecked")
		List<Service> services = (List<Service>)ReflectionTestUtils.getField(
				serviceContainerWithoutAutoStart, "services");
		
		assertThat(services.size(), is(1));
		assertThat(services.get(0) instanceof StartCounterService, is(true));
		StartCounterService service0 = (StartCounterService)services.get(0);
		assertThat(service0.isStarted(), is(false));
		assertThat(service0.getName(), is("service0"));

		// Manually start container
		serviceContainerWithoutAutoStart.start();
		assertThat(serviceContainerWithoutAutoStart.isStarted(), is(true));
		
		// Since service container use thread pool, service might not started before we reach here.
		// so we must wait a bit.
		long maxWaitTimeMillis = 2000;
		long startTimeMillis = System.currentTimeMillis();
		while(!service0.isStarted() && (System.currentTimeMillis() - startTimeMillis) < maxWaitTimeMillis)
			Thread.sleep(500);
		assertThat(service0.isStarted(), is(true));
		
		assertThat(service0.startCount, is(1));
		assertThat(service0.stopCount, is(0));
		
		// Ensure start twice in row will not affect startCount
		serviceContainerWithoutAutoStart.start();
		assertThat(service0.isStarted(), is(true));
		assertThat(service0.startCount, is(1));
		assertThat(service0.stopCount, is(0));
		
		// Manually stop container
		serviceContainerWithoutAutoStart.stop();
		assertThat(serviceContainerWithoutAutoStart.isStarted(), is(false));
		assertThat(service0.isStarted(), is(false));
		assertThat(service0.startCount, is(1));
		assertThat(service0.stopCount, is(1));

		// Ensure stop twice in row will not affect stopCount
		serviceContainerWithoutAutoStart.stop();
		assertThat(service0.isStarted(), is(false));
		assertThat(service0.startCount, is(1));
		assertThat(service0.stopCount, is(1));
	}
	
	@Test
	public void testServiceContainerWithAutoStart() throws Exception {
		assertThat(serviceContainerWithAutoStart.isStarted(), is(true));

		@SuppressWarnings("unchecked")
		List<Service> services = (List<Service>)ReflectionTestUtils.getField(
				serviceContainerWithAutoStart, "services");
		
		assertThat(services.size(), is(2));
		assertThat(services.get(0) instanceof StartCounterService, is(true));
		assertThat(services.get(1) instanceof StartCounterService, is(true));
		StartCounterService service0 = (StartCounterService)services.get(0);
		StartCounterService service1 = (StartCounterService)services.get(1);
		assertThat(service0.isStarted(), is(true));
		assertThat(service0.getName(), is("service0"));
		assertThat(service1.isStarted(), is(true));
		assertThat(service1.getName(), is("service1"));
		
		// Manually close container
		serviceContainerWithAutoStart.stop();
		assertThat(serviceContainerWithAutoStart.isStarted(), is(false));
		assertThat(service0.isStarted(), is(false));
		assertThat(service1.isStarted(), is(false));
	}
}

