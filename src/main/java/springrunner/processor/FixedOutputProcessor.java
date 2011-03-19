package springrunner.processor;

import springrunner.data.Nil;

/**
 * Take in any data and discard it and returned a fixed predefined data back.
 *
 * @author Zemian Deng
 */
public class FixedOutputProcessor<T> extends AbstractProcessor<Object, T> implements Runnable {
	
	private T output;
	
	public void setOutput(T output) {
		this.output = output;
	}

	@Override
	public T process(Object data) {
		logger.debug("Received " + data +", returning " + output);
		return output;
	}

	@Override
	public void run() {
		logger.debug("Running process with Nil");
		process(Nil.VALUE);		
	}
	
}
