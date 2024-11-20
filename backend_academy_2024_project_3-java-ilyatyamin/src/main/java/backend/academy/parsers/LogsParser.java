package backend.academy.parsers;

import java.util.stream.Stream;

public interface LogsParser {
    Stream<String> parse(String path);
}
