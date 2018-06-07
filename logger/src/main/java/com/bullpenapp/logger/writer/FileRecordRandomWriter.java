package com.bullpenapp.logger.writer;

import java.io.IOException;
import java.nio.file.Path;

public class FileRecordRandomWriter extends AbstractFileWriter implements RecordWriter, AutoCloseable {

	public FileRecordRandomWriter(final String filepath, final String fileName) {
		super(filepath, fileName);
	}

	/* (non-Javadoc)
	 * @see com.bullpenapp.logger.writer.AbstractFileWriter#doWrite(java.nio.file.Path, byte[])
	 */
	@Override
	public boolean doWrite(final Path logFile, final byte[] bytes) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub

	}

}
