package springrunner;

import springrunner.AbstractService;

/**
 * A service for unit testing. It counts number of start and stop been invoked.
 *
 * @author Zemian Deng
 */
public class StartCounterService extends AbstractService {
	public int startCount = 0;
	public int stopCount = 0;
	public StartCounterService() {
	}
	public StartCounterService(String name) {
		setName(name);
	}
	@Override
	protected void startService() {
		startCount ++;
	}

	@Override
	protected void stopService() {
		stopCount++;
	}
}
