package springrunner.processor.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import springrunner.processor.AbstractProcessor;

/**
 * Move an input file into a directory. It output the new file after it moved.
 * 
 * @author Zemian Deng
 */
public class MoveFile extends AbstractProcessor<File, File> {
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
			logger.debug("Moving file=" + file + " toDir=" + dir);
			FileUtils.moveFileToDirectory(file, dir, autoCreateDir);
			File newFile = new File(dir, file.getName());
			return newFile;
		} catch (IOException e) {
			throw new RuntimeException("Failed to move file: " + file, e);
		}
	}
}
