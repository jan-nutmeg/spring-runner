package org.springrunner.service;


/**
 * A base service extends {@link AbstractService} and provide command
 * line arguments when run with {@link ServiceRunner}.
 * 
 * @see ServiceRunner
 * 
 * @author Zemian Deng
 */
public abstract class AbstractCmdArgsService extends AbstractService implements ArgumentsSetter {

	protected String[] arguments;
	
	@Override
	public void setArguments(String[] args) {
		this.arguments = args;
	}
	
}
