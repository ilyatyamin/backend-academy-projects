package backend.academy.exceptions;

public class ConfigFileNotFoundException extends RuntimeException {
    public ConfigFileNotFoundException(String message) {
        super(message);
    }
}
