package springrunner.processor;

/**
 * A processor is function of one input data type I, process it and then return a data type O.
 * 
 * @author Zemian Deng
 */
public interface Processor<I, O> {
	O process(I data);
}
