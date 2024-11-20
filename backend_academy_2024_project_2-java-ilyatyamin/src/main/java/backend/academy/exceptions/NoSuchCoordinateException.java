package backend.academy.exceptions;

public class NoSuchCoordinateException extends RuntimeException {
    public NoSuchCoordinateException(String message) {
        super(message);
    }
}
