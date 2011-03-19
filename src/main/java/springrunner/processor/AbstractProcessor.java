package springrunner.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A base processor that provide a instance logger for subclass to use.
 * 
 * @author Zemian Deng
 */
public abstract class AbstractProcessor<I, O> implements Processor<I, O> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

}
