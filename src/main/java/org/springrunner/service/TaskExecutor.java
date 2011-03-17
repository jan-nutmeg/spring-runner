package org.springrunner.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Starts a predefined list of {@link Runnable} tasks upon start() of this service, and it will shutdown the threadPool upon stop().
 * 
 * <p>
 * You may use this class in Spring configuration to kick start tasks!
 * 
 * @author Zemian Deng
 */
public class TaskExecutor implements Service {
	
	private static Logger logger = LoggerFactory.getLogger(TaskExecutor.class);
	
	private ExecutorService threadPool = Executors.newFixedThreadPool(1);

	private List<Runnable> tasks = new ArrayList<Runnable>();

	public void setTasks(List<Runnable> tasks) {
		this.tasks = tasks;
	}

	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	public void start() {
		logger.debug("Starting " + tasks.size() + " tasks with thread pool: " + threadPool);
		for (Runnable task : tasks) {
			logger.debug("Starting task: " + task);
			threadPool.execute(task);
		}
	}

	public void stop() {
		logger.debug("Stopping threadPool " + threadPool);
		threadPool.shutdownNow();
	}
}
