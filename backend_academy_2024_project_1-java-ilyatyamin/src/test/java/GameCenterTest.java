import backend.academy.game.GameCenter;
import backend.academy.game.GameSession;
import backend.academy.iohandlers.InputHandler;
import backend.academy.iohandlers.OutputHandler;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GameCenterTest {
    @Test
    void testJustCreate() throws IOException {
        // arrange
        String potentialUserInput = "-";
        Scanner scanner = new Scanner(potentialUserInput);

        StringBuilder string = new StringBuilder();
        // Special handler for writing to the string
        OutputHandler outputHandler = new OutputHandler(new OutputStream() {
            @Override
            public void write(int b) {
                string.append((char) b);
            }

            public String toString() {
                return string.toString();
            }
        });
        InputHandler inputHandler = new InputHandler(scanner, outputHandler);
        GameCenter center = new GameCenter(inputHandler, outputHandler);

        // act
        GameSession session = center.createGame();

        // assert
        assertThat(session).isNotNull();
    }
}
