package springrunner.processor;

import org.apache.log4j.Logger;

/**
 * A base processor that provide a instance logger for subclass to use.
 * 
 * @author Zemian Deng
 */
public abstract class AbstractProcessor<I, O> implements Processor<I, O> {
	/** A log4j logger. */
	protected Logger logger = Logger.getLogger(getClass());

}
