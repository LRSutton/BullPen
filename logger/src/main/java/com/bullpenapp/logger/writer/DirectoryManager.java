package com.bullpenapp.logger.writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DirectoryManager {
	@Value("${service.logger.permissions.directory.default:rwxr-x---}")
	private String defaultDirectoryPerms;

	final private static Logger LOG = LoggerFactory.getLogger(DirectoryManager.class);
	final private Predicate<Path> fileExists = p -> Files.exists(p);

	public void assertDirectoryExists(final Path logPath, final boolean createIfMissing) throws IOException {
		this.assertDirectoryExists(logPath, createIfMissing, defaultDirectoryPerms);
	}

	public void assertDirectoryExists(final Path logPath, final boolean createIfMissing, final String perms) throws IOException {
		final boolean exists = fileExists.test(logPath);

		if (!exists) {
			if (createIfMissing) {
				_createDirectory(logPath, perms);
			} else {
				throw new FileNotFoundException(String.format("Required directory %s does not exist.", logPath));
			}
		}
	}

	private void _createDirectory(final Path logPath, final String dirPerms) throws IOException {
		final Set<PosixFilePermission> perms = PosixFilePermissions.fromString(dirPerms);
		final FileAttribute<Set<PosixFilePermission>> attrs = PosixFilePermissions.asFileAttribute(perms);
		try {
			Files.createDirectories(logPath, attrs);
		} catch (final FileAlreadyExistsException ex) {
			LOG.info("Attempt to create missing directory somehow already exists: " + ex.getMessage());
		}
	}


}
