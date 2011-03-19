package springrunner.processor.file;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import springrunner.processor.AbstractProcessor;

/**
 * Take a text input and write it to a file in the {@link #dir}. The file name will be auto generated
 * using a UUID hex string.
 * 
 * @author Zemian Deng
 */
public class TextFileWriter extends AbstractProcessor<String, File> {
	
	private File dir;
	
	public void setDir(File dir) {
		this.dir = dir;
	}
	
	@Override
	public File process(String data) {
		String filename = UUID.randomUUID().toString();
		File file = new File(dir, filename);
		try {
			logger.debug("Writing file: " + file);
			FileUtils.writeStringToFile(file, data);
			return file;
		} catch (IOException e) {
			throw new RuntimeException("Failed to move file: " + file, e);
		}
	}

}
