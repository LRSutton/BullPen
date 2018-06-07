package com.bullpenapp.logger.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileRecordNioWriter extends AbstractFileWriter implements RecordWriter {

	public FileRecordNioWriter(final String recordPath, final String filename) {
		super(recordPath, filename);
	}

	/* (non-Javadoc)
	 * @see com.bullpenapp.logger.writer.AbstractFileWriter#doWrite(java.nio.file.Path, byte[])
	 */
	@Override
	public boolean doWrite(final Path logFile, final byte[] bytes) throws IOException {
		Files.write(logFile, bytes, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		return true;
	}
}
