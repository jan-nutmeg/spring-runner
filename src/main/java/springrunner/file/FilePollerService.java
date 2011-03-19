package springrunner.file;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import springrunner.AbstractService;
import springrunner.data.Nil;
import springrunner.processor.Processor;

/**
 * A simple file poller service that will poll a {@link #dir} and call {@link #fileProcessor}
 * on each file found. The timer may be configured with a start delay
 * and period of interval in milliseconds.
 *  
 * <p>
 * The poller timer is setup to ensure the {@link #fileProcessor} is block until it finish
 * before the next timer tick goes off. This means if {@link #fileProcessor} is taking longer than
 * the timer interface, it will skip the timer tick as needed to wait for processing is done.
 * 
 * @author Zemian Deng
 */
public class FilePollerService extends AbstractService {
	private Processor<File, ?> fileProcessor;
	private Timer timer;
	private long delay;
	private long period;
	private File dir;
	
	public void setDir(File dir) {
		this.dir = dir;
	}
	public void setDelay(long delay) {
		this.delay = delay;
	}

	public void setPeriod(long period) {
		this.period = period;
	}
	public void setFileProcessor(Processor<File, Nil> fileProcessor) {
		this.fileProcessor = fileProcessor;
	}
	
	private void pollDir() {
		File[] files = dir.listFiles();
		for (File file : files) {
			fileProcessor.process(file);
		}
	}
	
	@Override
	public void startService() {
		// We don't want start the poller if directories is not valid
		logger.debug("Verifying file poller directories.");
		if (!dir.isDirectory())
			throw new RuntimeException(dir + " is not a directory.");
		
		// Everything is ready, let's start
		logger.debug("Starting file poller: dir=" + dir + ", period=" + period + ", delay=" + delay);
		timer = new Timer();
		timer.schedule(new FilePollerTask(), delay, period);
	}
	
	@Override
	public void stopService() {
		logger.debug("Stoping file poller.");
		timer.cancel();
	}
	
	private class FilePollerTask extends TimerTask {
		@Override
		public void run() {
			pollDir();
		}
	}
}
