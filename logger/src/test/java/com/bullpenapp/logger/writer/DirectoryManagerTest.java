package com.bullpenapp.logger.writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class DirectoryManagerTest extends AbstractFileWriterTest {
	@Autowired
	private DirectoryManager target;

	@Test
	public void testForDir_createDirectory() throws InvalidPathException, IOException {
		target.assertDirectoryExists(getLoggerPath(), true);
	}

	@Test(expected=FileNotFoundException.class)
	public void testForDir_throwError() throws InvalidPathException, IOException {
		target.assertDirectoryExists(getLoggerPath(), false);
	}


}
