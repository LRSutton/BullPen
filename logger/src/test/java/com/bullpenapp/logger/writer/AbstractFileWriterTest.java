package com.bullpenapp.logger.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public abstract class AbstractFileWriterTest {
	final private static Logger LOG = LoggerFactory.getLogger(AbstractFileWriterTest.class);

	@Value("${service.logger.path}")
	private String loggerPathStr;
	private Path loggerPath;

	/*
	 * Not worried about cross-platform ability here. Ensuring we have a /tmp
	 * dir (POSIX) and that we have a new, empty directory to test with.
	 */
	final private static Path ROOT_PATH = Paths.get("/tmp");
	final private static Pattern CHILD_PATH_PATTERN = Pattern.compile("^[a-zA-Z].*$");
	final private static Function<Path, String> CHILD_PATH_FUNC = p -> ROOT_PATH.relativize(p).toString();

	@After
	public void removeTestDir() throws IOException {
		if (!Files.exists(ROOT_PATH)) {
			throw new RuntimeException("Unable to complete tests. Need a /tmp directory");
		}

		if (!CHILD_PATH_PATTERN.matcher(CHILD_PATH_FUNC.apply(getLoggerPath())).matches()) {
			throw new RuntimeException(
					String.format("Cowardly refusing to delete anything except those files in /tmp (%s)", loggerPath));
		}

		_removeDir(getLoggerPath());
	}

	private void _removeDir(final Path path) throws IOException {
		assert path != null : "Path was null";
		System.out.println("path: " + path);

		if (!Files.exists(path)) {
			LOG.info("{} does not exist. skipping", path);
		} else {
			Files.list(path).forEach(file -> {
				if (Files.isDirectory(file)) {
					_deleteDir(file);
				} else {
					deleteFile(file);
				}
			});
			Files.deleteIfExists(path);
		}
	}

	private void _deleteDir(final Path path) {
		try {
			_removeDir(path);
		} catch (final IOException ex) {
			LOG.error("Unable to delete {}: {}", path, ex.getMessage());
		}

	}

	private void deleteFile(final Path path) {
		try {
			Files.deleteIfExists(path);
		} catch (final IOException ex) {
			LOG.error("Unable to delete {}: {}", path, ex.getMessage());
		}
	}

	/**
	 * @return the loggerPath
	 */
	protected String getLoggerPathStr() {
		return loggerPathStr;
	}

	/**
	 * @return the loggerPath
	 */
	protected Path getLoggerPath() {
		if (loggerPath == null) {
			loggerPath = Paths.get(loggerPathStr);
		}
		return loggerPath;
	}
}
