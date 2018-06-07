package com.bullpenapp.logger;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import com.bullpenapp.logger.writer.AbstractFileWriterTest;

@ActiveProfiles({"test","RANDOM_RECORD_WRITER"})
public class FileRecordRandomWriterTest extends AbstractFileWriterTest {

	//	@Autowired
	//	private RecordWriter target;

	@Test
	public void simpleRandomAccessWriteTest() {
		// TBD
	}
}
