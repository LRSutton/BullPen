package com.bullpenapp.logger.writer;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractFileWriter {
	final private static Logger LOG = LoggerFactory.getLogger(AbstractFileWriter.class);

	@Autowired
	DirectoryManager manager;

	@Value("${service.logger.file.create}")
	private boolean createTargetsIfMissing;

	/*
	 * The system path for the file to be written
	 */
	private transient final String path;
	/*
	 * The filename of the file to be written
	 */
	private transient final String filename;

	public AbstractFileWriter(final String loggerPath, final String filename) {
		this.path = loggerPath;
		this.filename = filename;
	}

	public abstract boolean doWrite(Path logFile, byte[] bytes) throws IOException;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.bullpenapp.logger.RecordWriter#write(java.lang.String)
	 */
	final public boolean write(final String record) throws IOException, InvalidPathException {
		boolean response = false;
		if (record != null && !record.isEmpty()) {
			response = writeRecipe(record);
		}
		return response;
	}

	private boolean writeRecipe(final String record) throws IOException, InvalidPathException {
		assert record != null : "Record was null";
		boolean success;

		final Path logFile = assertFileExists(Paths.get(path), Paths.get(filename), createTargetsIfMissing);

		try {
			success = doWrite(logFile, record.getBytes());
		} catch (final Exception ex) {
			success = handleException(ex);
		}

		return success;
	}

	protected boolean handleException(final Exception ex) throws IOException, InvalidPathException {
		LOG.error("Error writing log record: " + ex.getMessage());
		return false;
	}

	protected Path assertFileExists(final Path targetPath, final Path targetFilename, final boolean createIfMissing)
			throws IOException {
		manager.assertDirectoryExists(targetPath, createIfMissing);

		final Path logFile = targetPath.resolve(targetFilename).normalize();
		LOG.debug("File: {}", logFile);

		return logFile;
	}
}
