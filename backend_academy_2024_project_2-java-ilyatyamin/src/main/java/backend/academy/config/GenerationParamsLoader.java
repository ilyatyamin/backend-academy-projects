package backend.academy.config;

import backend.academy.exceptions.ConfigFileNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class GenerationParamsLoader {
    private static final String PATH_TO_CONFIG = "config.json";

    public InputStream getContext() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(PATH_TO_CONFIG);
    }

    public GenerationParams loadFromConfigFile() {
        InputStream is = getContext();

        if (is == null) {
            throw new ConfigFileNotFoundException("Config file not found");
        }

        ObjectMapper jsonCreator = new ObjectMapper();
        Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
        try {
            return jsonCreator.readValue(reader, GenerationParams.class);
        } catch (IOException e) {
            throw new RuntimeException("Incorrect file got, please check your file" + e.getMessage());
        }
    }
}
