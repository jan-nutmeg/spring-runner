package springrunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class to let subclass inherit a logger.
 * 
 * @author Zemian Deng
 */
public abstract class TestBase
{
	protected Logger logger = LoggerFactory.getLogger(getClass());
}
