package com.bullpenapp.logger.writer;

import java.io.IOException;
import java.nio.file.InvalidPathException;

public interface RecordWriter {

	boolean write(String value) throws IOException, InvalidPathException;

}
