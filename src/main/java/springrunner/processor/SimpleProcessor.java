package springrunner.processor;

/**
 * This is a simple processor that returns whatever that is passing in, and log a INFO level with the data.
 * 
 * @author Zemian Deng
 */
public class SimpleProcessor extends AbstractProcessor<Object, Object> {
	@Override
	public Object process(Object data) {
		logger.info("Received data: " + data);
		return data;
	}
}
