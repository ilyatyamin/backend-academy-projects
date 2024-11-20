package backend.academy.iohandlers;

import backend.academy.exceptions.IncorrectStringException;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Predicate;
import lombok.SneakyThrows;

public class InputHandler {
    private final Scanner stream;
    private final OutputHandler outputHandler;

    private final static String STANDARD_ERR_MESSAGE = "Your input is incorrect. Please input again. \n";

    public InputHandler(Scanner stream, OutputHandler outputHandler) {
        this.stream = stream;
        this.outputHandler = outputHandler;
    }

    /**
     * Read string from InputStream. If string does not fit to predicate (conditionToPass), exception has thrown
     *
     * @param conditionToPass  needed predicate to pass
     * @param exceptionMessage message with that exception will be thrown if user's input are incorrect
     * @return String - user's input
     */
    private String readString(
        Predicate<String> conditionToPass,
        String exceptionMessage
    ) {
        String line = stream.nextLine();
        if (!conditionToPass.test(line)) {
            throw new IncorrectStringException(exceptionMessage);
        }
        return line;
    }

    /**
     * Reads a line from InputStream until it matches a predicate.
     *
     * @param conditionToPass  needed predicate to pass
     * @param exceptionMessage message that user got if his/her input is incorrect
     * @return String - user's input
     */
    public String readStringUntil(
        Predicate<String> conditionToPass,
        String exceptionMessage
    ) throws IOException {
        String output = "";
        boolean isReaded = false;
        do {
            try {
                output = readString(conditionToPass, exceptionMessage);
                isReaded = true;
            } catch (Exception ex) {
                outputHandler.print(ex.getMessage(), Color.RED);
            }
        } while (!isReaded);
        return output;
    }

    /**
     * Reads a line from InputStream until it matches a predicate.
     *
     * @param conditionToPass needed predicate to pass
     * @return String - user's input
     */
    @SneakyThrows
    public String readStringUntil(Predicate<String> conditionToPass) {
        return readStringUntil(conditionToPass, STANDARD_ERR_MESSAGE);
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
