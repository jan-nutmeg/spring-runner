package springrunner.processor.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import springrunner.processor.AbstractProcessor;

/**
 * Read a input file and return a text string.
 * 
 * @author Zemian Deng
 */
public class TextFileReader extends AbstractProcessor<File, String> {

	@Override
	public String process(File file) {
		try {
			logger.debug("Reading file: " + file);
			String text = FileUtils.readFileToString(file); 
			return text;
		} catch (IOException e) {
			throw new RuntimeException("Failed to move file: " + file, e);
		}
	}

}
