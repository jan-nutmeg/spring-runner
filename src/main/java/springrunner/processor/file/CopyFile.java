package springrunner.processor.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import springrunner.processor.AbstractProcessor;

/**
 * Copy an input file into a directory. It output the new file after it moved.
 * 
 * @author Zemian Deng
 */
public class CopyFile extends AbstractProcessor<File, File> {
	private File dir;
	private boolean autoCreateDir;
	
	public void setDir(File dir) {
		this.dir = dir;
	}
	public void setAutoCreateDir(boolean autoCreateDir) {
		this.autoCreateDir = autoCreateDir;
	}

	@Override
	public File process(File file) {
		try {
			logger.debug("Copying file=" + file + " toDir=" + dir);
			FileUtils.copyFileToDirectory(file, dir, autoCreateDir);
			File newFile = new File(dir, file.getName());
			return newFile;
		} catch (IOException e) {
			throw new RuntimeException("Failed to copy file: " + file, e);
		}
	}
}
