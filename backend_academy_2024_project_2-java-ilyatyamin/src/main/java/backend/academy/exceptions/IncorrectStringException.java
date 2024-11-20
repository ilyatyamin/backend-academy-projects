package backend.academy.exceptions;

/**
 * Exception for incorrect string causes.
 * For example, it can be thrown when the system excepts that len of string is 1, but it doesn't
 */
public class IncorrectStringException extends RuntimeException {
    public IncorrectStringException(String message) {
        super(message);
    }

}
