package org.springframework.diva.app.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileStream {

	public Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
		Set<String> fileList = new HashSet<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
			for (Path path : stream) {
				//if (!Files.isDirectory(path)) {
					fileList.add(path.getFileName()
						.toString());
				//}
			}
		}
		System.out.println(fileList);
		return fileList;
	}

	public Set<String> listFilesUsingJavaIO(String dir) {
		return Stream.of(new File(dir).listFiles())
			.filter(file -> !file.isDirectory())
			.map(File::getName)
			.collect(Collectors.toSet());
	}
}
