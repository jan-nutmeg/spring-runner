package springrunner.processor.file;

import java.io.File;

import springrunner.data.Nil;
import springrunner.processor.AbstractProcessor;

/**
 * Delete an input file and return Nil.
 * 
 * @author Zemian Deng
 */
public class DeleteFile extends AbstractProcessor<File, Nil> {
	@Override
	public Nil process(File file) {
		logger.debug("Deleting file=" + file);
		if(!file.delete())
			throw new RuntimeException("Failed to delete file: " + file);
		return Nil.VALUE;
	}
}
