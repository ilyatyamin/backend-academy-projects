package backend.academy.iohandlers;

import backend.academy.enums.Color;
import java.io.IOException;
import java.io.OutputStream;

public class OutputHandler {
    private final OutputStream out;
    private static final String ANSI_RESET = "\u001B[0m";

    public OutputHandler(OutputStream outStream) {
        this.out = outStream;
    }

    /**
     * Writes data to the OutputStream
     *
     * @param data String that needed to be written
     * @throws IOException if there are some problems with your OutputStream
     */
    public void print(String data) throws IOException {
        out.write(data.getBytes());
    }

    /**
     * Writes data to the OutputStream and paints it to color
     *
     * @param data String that needed to be written
     * @throws IOException if there are some problems with your OutputStream
     */
    public void print(String data, Color color) throws IOException {
        out.write((color.strPresent() + data + ANSI_RESET).getBytes());
    }

    /**
     * Writes data to the OutputStream and finish input by new line
     *
     * @param data String that needed to be written
     * @throws IOException if there are some problems with your OutputStream
     */
    public void printLn(String data) throws IOException {
        out.write(data.getBytes());
        out.write("\n".getBytes());
    }

    /**
     * Writes data to the OutputStream, paints it to color and finish input by new line
     *
     * @param data String that needed to be written
     * @throws IOException if there are some problems with your OutputStream
     */
    public void printLn(String data, Color color) throws IOException {
        out.write((color.strPresent() + data + ANSI_RESET).getBytes());
        out.write("\n".getBytes());
    }

    /**
     * Clear output stream (flush it)
     *
     * @throws IOException if there are some problems with your OutputStream
     */
    public void clear() throws IOException {
        // magic :)
        print("\033[H\033[2J");
    }
}
