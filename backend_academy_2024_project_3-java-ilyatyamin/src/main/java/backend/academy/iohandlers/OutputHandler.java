package backend.academy.iohandlers;

import java.io.PrintStream;
import lombok.SneakyThrows;

public class OutputHandler {
    private final PrintStream out;
    private static final String ANSI_RESET = "\u001B[0m";

    public OutputHandler(PrintStream outStream) {
        this.out = outStream;
    }

    /**
     * Writes data to the OutputStream
     *
     * @param data String that needed to be written
     */
    @SneakyThrows
    public void print(String data) {
        out.write(data.getBytes());
    }

    /**
     * Writes data to the OutputStream and paints it to color
     *
     * @param data String that needed to be written
     */
    @SneakyThrows
    public void print(String data, Color color) {
        out.write((color.strPresent() + data + ANSI_RESET).getBytes());
    }

    /**
     * Writes data to the OutputStream and finish input by new line
     *
     * @param data String that needed to be written
     */
    @SneakyThrows
    public void printLn(String data) {
        out.write(data.getBytes());
        out.write("\n".getBytes());
    }

    /**
     * Writes data to the OutputStream, paints it to color and finish input by new line
     *
     * @param data String that needed to be written
     */
    @SneakyThrows
    public void printLn(String data, Color color) {
        out.write((color.strPresent() + data + ANSI_RESET).getBytes());
        out.write("\n".getBytes());
    }
}
