package com.bullpenapp.logger.writer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class RegexTest {

	@Test
	public void wrongWayToTestDirectoryInclusion() {
		final Path root = Paths.get("/tmp");
		final Path path0 = Paths.get("/");
		final Path path1 = Paths.get("/opt/location/sub");
		final Path path2 = Paths.get("/tmp/location/sub");

		final Pattern ptrn = Pattern.compile("^[a-zA-Z].*$");
		final Function<Path, String> f = p -> root.relativize(p).toString();

		Assert.assertFalse("root",ptrn.matcher(f.apply(root)).matches());
		Assert.assertFalse("path0",ptrn.matcher(f.apply(path0)).matches());
		Assert.assertFalse("path1",ptrn.matcher(f.apply(path1)).matches());
		Assert.assertTrue("path2",ptrn.matcher(f.apply(path2)).matches());
	}

	@Test
	public void rightWayToTestDirectoryInclusion() {
		final Path root = Paths.get("/tmp");
		final Path path0 = Paths.get("/");
		final Path path1 = Paths.get("/opt/location/sub");
		final Path path2 = Paths.get("/tmp/location/sub");

		Assert.assertTrue("root",root.startsWith(root));
		Assert.assertFalse("path0",path0.startsWith(root));
		Assert.assertFalse("path1",path1.startsWith(root));
		Assert.assertTrue("path2",path2.startsWith(root));
	}
}
