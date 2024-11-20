package backend.academy.exceptions;

public class InvalidCommandLineArgsException extends RuntimeException {
    public InvalidCommandLineArgsException(String message) {
        super(message);
    }
}
