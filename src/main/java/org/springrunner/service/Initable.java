package org.springrunner.service;

/**
 * A lifecycle interface that provide init and destroy methods.
 * 
 * <p>
 * Note that a Service is not required to implements this interface. It is provide per
 * user case purpose.
 *
 * @author Zemian Deng
 */
public interface Initable {
	void init();
	void destroy();
}
