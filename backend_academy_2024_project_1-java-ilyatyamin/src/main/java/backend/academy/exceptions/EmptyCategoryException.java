package backend.academy.exceptions;

/**
 * This exception for causes when questions with pointed category do not exist
 */
public class EmptyCategoryException extends RuntimeException {
    public EmptyCategoryException(String message) {
        super(message);
    }

}
