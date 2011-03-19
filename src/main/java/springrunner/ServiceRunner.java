package springrunner;

import java.util.Arrays;

/**
 * Bootstrap and run any target {@link Service} by calling their start() and stop() methods.
 * 
 * <p>
 * Upon run() begins, it will register a Java shutdown hook to notify itself when
 * user sends KILL signal (eg: CTRL+C). It then invoke start() method, and then put it self into 
 * a wait state on the current thread until notified. Upon received a shutdown event, it will
 * notify the waiting thread to wake up, and then it invoke the shutdownRun(), which
 * in turn calls the stop() method.
 * 
 * <p>
 * The target service instance is created by using the first argument to command line as classname.
 * It will create a instance using default constructor. Or subclass may just override the 
 * {@link #createService()} method to provide their own custom target service. 
 * 
 * <p>
 * A target service may optionally implements {@link ArgumentsListener}, and then rest of the command 
 * line arguments (minus its self target service classname) will be set. Also if target service
 * implements {@link Initable}, then init() will be invoked before start(), and destroy() will be
 * invoked after stop().
 * 
 * <p>
 * If user set {@link #waitAndNotify} to false (which can be set by System property also), then 
 * the run() method will start() without go into wait mode but invoke the stop() immediately.
 * 
 * <p>
 * This class also provides a static main entry for Java command line. It will pass in command args 
 * as constructor parameter, and then invoke run().
 * 
 * <p>
 * Example: 
 * <pre>
 * java ServiceRunner -DwaitAndNotify=true org.springrunner.service.spring.SpringService
 * </pre>
 * 
 * @author Zemian Deng
 */
public class ServiceRunner extends AbstractService implements Runnable {
	
	protected String[] arguments;
	protected Service service;
	protected boolean waitAndNotify;
	protected boolean running;

	public void setService(Service service) {
		this.service = service;
	}
	public void setWaitAndNotify(boolean waitAndNotify) {
		this.waitAndNotify = waitAndNotify;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	protected Service createService() {
		String serviceClassName = arguments[0];
		logger.debug("Creating new service with " + serviceClassName);
		try {
			Class<?> serviceClass = Class.forName(serviceClassName);
			if (!Service.class.isAssignableFrom(serviceClass)) {
				throw new RuntimeException("Service class " + serviceClassName + " did not implements " + Service.class.getName());
			}
			Object serviceObject = serviceClass.newInstance();
			return (Service) serviceObject;
		} catch (Exception e) {
			throw new RuntimeException("Failed to create service with " + serviceClassName, e);
		}
	}

	public void init() {
		if (service == null)
			service = createService();
		
		checkAndSetArgumentListener();
	}
	
	public void checkAndSetArgumentListener() {
		if (service instanceof ArgumentsListener) {
			String[] serviceArgs = Arrays.copyOfRange(arguments, 1, arguments.length);
			logger.debug("Setting service with arguments=" + Arrays.asList(serviceArgs));
			((ArgumentsListener)service).onArguments(serviceArgs);
		}
	}

	@Override
	protected void startService() {
		logger.info("Starting " + service);
		
		if (service instanceof Initable)
			((Initable)service).init();
		
		service.start();
		
		logger.info(service + " started.");
	}

	@Override
	protected void stopService() {
		service.stop();
		
		if (service instanceof Initable)
			((Initable)service).destroy();
		
		logger.info(service + " stopped.");
	}

	@Override
	public void run() {
		if (running) {
			logger.debug("No action. " + name + " is already running.");
			return;
		}
		running = true;
		
		logger.debug("Running " + name);
		start();
		if (waitAndNotify) {
			registerShutdownEvent();

			logger.info("Server is ready.");
			waitForShutdownEvent();
			shutdownRun();
		} else {
			stop();
		}
		// regardless of processing, we are done running at this point.
		running = false;
	}
	
	/** Clean up and stop of what {@link #run()} started. */
	public void shutdownRun() {
		if (!running) {
			logger.debug("No action. " + name + " is not running yet.");
			return;
		}
		running = false;
		
		logger.debug("Begin to shutdown " + name);
		stop();
		logger.info(name + " is done.");
	}

	protected void waitForShutdownEvent() {
		synchronized (this) {
			try {
				logger.debug("Wait and block current thread.");
				this.wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	protected void notifyShutdownEvent() {
		logger.debug("Notify all waiting thread to wake up.");
		synchronized (this) {
			this.notifyAll();
		}
	}

	protected void registerShutdownEvent() {
		logger.debug("Registering a shutdown event with Java Runtime#shutdownHook.");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (running) {
					notifyShutdownEvent();
				}
			}
		});
	}

	public static void main(String[] args) {
		// Process helppage
		if (args.length < 1 || args[0].equals("-h")) {
			System.out.println(
				"Usage java [options] ServiceRunner <className>\n" +
				"\n" +
				"[options]\n" +
				"-DwaitAndNotify=false Do not wait and block after start service. Default true.\n" +
				"\n");
			return;
		}
		
		// Create, init, and run the main runner
		ServiceRunner main = new ServiceRunner();
		main.arguments = args;
		main.waitAndNotify = Boolean.valueOf(System.getProperty("waitAndNotify", "true"));
		
		main.init();
		
		main.run();
	}
}
