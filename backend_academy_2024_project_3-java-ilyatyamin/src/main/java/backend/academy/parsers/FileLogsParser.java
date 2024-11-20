package backend.academy.parsers;

import backend.academy.exceptions.InvalidPathProjectException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class FileLogsParser implements LogsParser {
    @Override
    public Stream<String> parse(String path) {
        // glob pattern matcher
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/" + path);
        List<Stream<String>> resultStream = new ArrayList<>();

        try {
            // try to get main project folder
            var absolute = Thread.currentThread().getContextClassLoader().getResource(".");
            if (absolute == null) {
                throw new InvalidPathProjectException("Check your path and repeat the input");
            }
            var absoluteURI = absolute.toURI();

            // walk through the tree and detect correct files
            Files.walkFileTree(Paths.get(absoluteURI), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(
                    Path path, BasicFileAttributes attrs
                ) throws IOException {
                    if (pathMatcher.matches(path)) {
                        resultStream.add(Files.lines(path));
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return resultStream.stream()
            .flatMap(Function.identity());
    }
}
