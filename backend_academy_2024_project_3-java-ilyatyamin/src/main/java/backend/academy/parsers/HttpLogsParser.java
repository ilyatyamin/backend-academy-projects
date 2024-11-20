package backend.academy.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class HttpLogsParser implements LogsParser {
    @Override
    public Stream<String> parse(String path) {
        try {
            URL url = URI.create(path).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            return new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)).lines();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
