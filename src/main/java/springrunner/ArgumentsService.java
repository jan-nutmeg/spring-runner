package springrunner;


/**
 * A base service extends {@link AbstractService} and provides command
 * line arguments when run with {@link ServiceRunner}.
 * 
 * @see ServiceRunner
 * 
 * @author Zemian Deng
 */
public abstract class ArgumentsService extends AbstractService implements ArgumentsListener {

	protected String[] arguments;
	
	@Override
	public void onArguments(String[] args) {
		this.arguments = args;
	}
	
}
