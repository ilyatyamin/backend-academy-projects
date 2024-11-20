import backend.academy.enums.Difficult;
import backend.academy.enums.GameState;
import backend.academy.game.GameInterface;
import backend.academy.game.GameSession;
import backend.academy.iohandlers.OutputHandler;
import backend.academy.wordstorage.Word;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.io.OutputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class GameInterfaceTest {
    @Test
    void testPrintAllStates() throws IOException {
        for (GameState state : GameState.values()) {
            // arrange
            Difficult currentDifficult = Difficult.EASY;
            String currentCategory = "Category";
            String currentName = "Name";
            Word word = new Word(currentCategory, currentName, currentDifficult);

            GameSession session = Mockito.mock(GameSession.class);
            Mockito.when(session.currentState()).thenReturn(state);
            Mockito.when(session.word()).thenReturn(word);
            Mockito.when(session.countAccessibleAttempts()).thenReturn(5);

            StringBuilder string = new StringBuilder();
            // Special handler for writing to the string
            OutputHandler handler = new OutputHandler(new OutputStream() {
                @Override
                public void write(int b) {
                    string.append((char) b);
                }

                public String toString() {
                    return string.toString();
                }
            });
            GameInterface gameInterface = new GameInterface(handler, session);

            // act
            gameInterface.printInviteToLog();

            // assert
            assertThat(string.toString()).isNotEmpty();


            // act
            gameInterface.printState();

            // assert
            assertThat(string.toString()).isNotEmpty();
        }
    }
}
