package springrunner.processor;

import java.util.ArrayList;
import java.util.List;

/**
 * A processor that holds a list of processors that forms a pipe line for data to flow through.
 * Data will pass from this processor input into the pipe. If {@link #usePipeflowOutput} is set
 * then it return the last output from the pipe, else it return the original input value for
 * this processor. If the any of the processors in the pipe returned a <null> value, 
 * then it stops processing the list immediately, thus breaking out of the flow.
 * 
 * @author Zemian Deng
 */
public class PipeflowProcessor<I> extends AbstractProcessor<I, Object> {
	private List<Processor<Object, Object>> processors = new ArrayList<Processor<Object, Object>>();
	private boolean usePipeflowOutput;
	
	public void setProcessors(List<Processor<Object, Object>> processors) {
		this.processors = processors;
	}
	public void setUsePipeflowOutput(boolean usePipeflowOutput) {
		this.usePipeflowOutput = usePipeflowOutput;
	}

	@Override
	public Object process(I data) {
		logger.debug("Pipeflow begin: " + data);
		Object pipedData = data;
		int i = 0;
		for (Processor<Object, Object> processor : processors) {
			pipedData = processor.process(pipedData);
			if (pipedData == null) {
				logger.debug("Pipeflow stopped at processor#" + i + ": " + processor);
				break;
			}
			i++;
		}
		logger.debug("Pipeflow completed: " + pipedData);
		
		if (usePipeflowOutput) {
			logger.debug("Returning last pipe output data:" + pipedData);
			return pipedData;
		}
		
		// return original input
		logger.debug("Returning original data:" + data);
		return data;
	}
}
