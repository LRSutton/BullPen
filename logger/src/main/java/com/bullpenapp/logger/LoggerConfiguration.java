package com.bullpenapp.logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bullpenapp.logger.writer.FileRecordNioWriter;
import com.bullpenapp.logger.writer.FileRecordRandomWriter;
import com.bullpenapp.logger.writer.RecordWriter;

@Configuration
public class LoggerConfiguration {

	@Value("${service.logger.path}")
	private String loggerPath;

	@Value("${service.logger.file.records}")
	private String loggerRecordsFileName;

	@Value("${service.logger.file.digest}")
	private String loggerDigestFileName;

	@Bean
	@Profile("NIO_RECORD_WRITER")
	public RecordWriter RecordNioWriter() {
		return new FileRecordNioWriter(loggerPath, loggerRecordsFileName);
	}

	@Bean
	@Profile("RANDOM_RECORD_WRITER")
	public RecordWriter RecordRandomWriter() {
		return new FileRecordRandomWriter(loggerPath, loggerRecordsFileName);
	}
}
