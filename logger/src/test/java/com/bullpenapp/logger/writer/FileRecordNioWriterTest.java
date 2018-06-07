package com.bullpenapp.logger.writer;

import java.io.IOException;
import java.nio.file.InvalidPathException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test","NIO_RECORD_WRITER"})
public class FileRecordNioWriterTest extends AbstractFileWriterTest {
	@Autowired
	private RecordWriter target;

	@Test
	public void testSimpleNioWrite() {
		final String value = "Wrote something to a file";
		try {
			final boolean success = target.write(value);
			Assert.assertTrue(success);
		} catch (IOException | InvalidPathException ex) {
			Assert.fail(ex.getMessage());
		}
	}
}
