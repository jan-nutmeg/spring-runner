package springrunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A container for a list of services that uses a thread pool to execute them all upon start(). 
 * Upon stop(), the container will stop all the services then the thread pool. Note that
 * the stop() is usually call by a separate thread, and they run in reverse order of the
 * list in serial fashion. User ought to ensure services not to block during stop() and 
 * exit quickly as possible.
 * 
 * <p>
 * This container will also automatically detect if service is type of {@link Initable}, and if it
 * is, then it will auto invoke init() and destroy() before and after start() and stop()
 * respectively. Note that all services' init() will be called before any start() will be run;
 * and all init() methods are run on single thread that started this container. Same applies to
 * the destroy() methods that they run after all services's stop() are done. Be sure not to block
 * thread in init() or destroy() methods, or this container will not terminate properly. 
 * 
 * <p>
 * We default to use only 1 thread size pool to start services in serialized fashion. 
 * User may customize a different thread pool or size for process the service list.
 * 
 * <p>
 * This is a great way to wrap all services in Spring configuration so that you don't 
 * have to explicitly use init-method and destroy-method on individual service bean.
 * 
 * @author Zemian Deng
 */
public class ServiceContainer extends AbstractService {

	private List<Service> services = new ArrayList<Service>();
	private ExecutorService threadPool = Executors.newFixedThreadPool(1);
	
	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}
	
	@Override
	public void startService() {
		logger.debug("Initializing " + name + " with " + services.size() + " services.");
		for (Service service : services) {
			if (service instanceof Initable) {
				logger.debug("Initializing service: " + service);
				((Initable)service).init();
			}
		}
		
		logger.debug("Starting " + name + " with " + services.size() + " services with threadPool " + threadPool);
		for (Service item : services) {
			final Service service = item;
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					logger.debug("Starting service: " + service);
					service.start();
				}
			});
		}
		
		logger.info(name + " started.");
	}

	@Override
	public void stopService() {
		int size = services.size();
		logger.debug("Stopping " + name + " with " + size + " services in reverse order.");
		if (size > 0) {
			for (int i = size - 1; i >= 0; i--) {
				final Service service = services.get(i);
				logger.debug("Stopping service: " + service);
				service.stop();
			}
			
			logger.debug("Destroying " + name + " with " + services.size() + " services in reverse order.");
			for (int i = size - 1; i >= 0; i--) {
				final Service service = services.get(i);
				if (service instanceof Initable) {
					logger.debug("Destroying service: " + service);
					((Initable)service).destroy();
				}
			}
		}
		
		logger.debug("Stopping threaPool " + threadPool);
		threadPool.shutdown();

		logger.info(name + " stopped.");
	}
	
	@Override
	public String toString() {
		return "ServiceContainer[" + name + ", " + services.size() + " services]";
	}
}
