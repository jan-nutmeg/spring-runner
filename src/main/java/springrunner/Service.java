package springrunner;

/**
 * A business service that needs start() and stop() lifecycle.
 * 
 * @author Zemian Deng
 */
public interface Service {
	void start();
	void stop();
}
