package backend.academy.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public final class ConfigLoader {
    private ConfigLoader() {
    }

    public static ConfigParams loadFromStream(InputStream is) {
        ObjectMapper jsonCreator = new ObjectMapper();
        Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
        try {
            return jsonCreator.readValue(reader, ConfigParams.class);
        } catch (IOException e) {
            throw new RuntimeException("Incorrect file got, please check your file" + e.getMessage());
        }
    }
}
