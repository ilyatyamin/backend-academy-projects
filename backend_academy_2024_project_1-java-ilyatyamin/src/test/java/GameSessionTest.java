import backend.academy.enums.Difficult;
import backend.academy.enums.GameState;
import backend.academy.exceptions.IncorrectStringException;
import backend.academy.game.GameSession;
import backend.academy.configs.ConfigParams;
import backend.academy.wordstorage.Word;
import backend.academy.wordstorage.WordStorage;
import org.apache.commons.math3.util.Pair;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class GameSessionTest {
    @Test
    void testNormalParams() {
        // arrange
        Difficult currentDifficult = Difficult.EASY;
        String currentCategory = "Category";
        String currentName = "Name";
        Word word = new Word(currentCategory, currentName, currentDifficult);
        int numberOfAttempts = 5;
        ConfigParams params = new ConfigParams(numberOfAttempts);
        WordStorage storage = Mockito.mock(WordStorage.class);

        Mockito.when(storage.getNotUsedWord(currentDifficult, currentCategory))
            .thenReturn(word);
        GameSession session = new GameSession(currentDifficult, currentCategory, storage, params);

        // act
        Word sessionWord = session.word();
        GameState sessionState = session.currentState();
        boolean sessionHintNeeded = session.isHintNeeded();
        boolean sessionPlayerCanInput = session.isPlayerCanInput();
        int sessionCountAvailableAttempts = session.countAvailableAttempts();
        int sessionCountAccessibleAttempts = session.countAccessibleAttempts();

        // assert
        assertThat(sessionWord).isEqualTo(word);
        assertThat(sessionState).isEqualTo(GameState.CREATED);
        assertThat(sessionHintNeeded).isEqualTo(false);
        assertThat(sessionPlayerCanInput).isEqualTo(true);
        assertThat(sessionCountAvailableAttempts).isEqualTo(numberOfAttempts);
        assertThat(sessionCountAccessibleAttempts).isEqualTo(numberOfAttempts);
    }

    @Test
    void testGuessInSession() {
        // arrange
        Difficult currentDifficult = Difficult.EASY;
        String currentCategory = "Category";
        String currentName = "Name";
        String currentHint = "Hint";
        Word word = new Word(currentName, currentCategory, currentDifficult, currentHint);
        int numberOfAttempts = 4;

        ConfigParams params = new ConfigParams(numberOfAttempts);

        WordStorage storage = Mockito.mock(WordStorage.class);
        Mockito.when(storage.getNotUsedWord(currentDifficult, currentCategory))
            .thenReturn(word);
        GameSession session = new GameSession(currentDifficult, currentCategory, storage, params);

        // act
        Word sessionWord = session.word();
        GameState sessionCurrentState = session.currentState();
        int sessionBadAttempts = session.badAttempts();

        List<Integer> badAttemptsCycle1 = new ArrayList<>();
        List<Integer> accessAttemptsCycle1 = new ArrayList<>();
        for (int i = 0; i < numberOfAttempts - 1; i++) {
            session.tryToGuess("_");
            badAttemptsCycle1.add(session.badAttempts());
            accessAttemptsCycle1.add(session.countAccessibleAttempts());
        }

        List<Pair<Integer, Integer>> badAttemptsAfterGoodAttempts = new ArrayList<>();
        for (char ch : "name".toCharArray()) {
            int currentAttempts = session.badAttempts();
            session.tryToGuess(String.valueOf(ch));
            badAttemptsAfterGoodAttempts.add(new Pair<>(currentAttempts, session.badAttempts()));
        }

        // assert
        assertThat(sessionWord).isEqualTo(word);
        assertThat(sessionCurrentState).isEqualTo(GameState.CREATED);
        assertThat(sessionBadAttempts).isEqualTo(0);

        for (int i = 0; i < numberOfAttempts - 1; i++) {
            assertThat(badAttemptsCycle1.get(i)).isEqualTo(i + 1);
            assertThat(accessAttemptsCycle1.get(i)).isEqualTo(numberOfAttempts);
        }

        for (Pair<Integer, Integer> badAttemptsAfterGoodAttempt : badAttemptsAfterGoodAttempts) {
            assertThat(badAttemptsAfterGoodAttempt.getFirst())
                .isEqualTo(badAttemptsAfterGoodAttempt.getSecond());
        }
        assertThat(session.currentState()).isEqualTo(GameState.WIN);
    }

    @Test
    void testLossInSession() {
        // arrange
        Difficult currentDifficult = Difficult.EASY;
        String currentCategory = "Category";
        String currentName = "Tinkoff";
        String currentHint = "Hint";

        Word word = new Word(currentName, currentCategory, currentDifficult, currentHint);
        int numberOfAttempts = 4;
        ConfigParams params = new ConfigParams(numberOfAttempts);

        // Mock setup
        WordStorage storage = Mockito.mock(WordStorage.class);
        Mockito.when(storage.getNotUsedWord(currentDifficult, currentCategory))
            .thenReturn(word);
        GameSession session = new GameSession(currentDifficult, currentCategory, storage, params);

        // act
        ThrowableAssert.ThrowingCallable lambda = () -> session.tryToGuess("TI");
        assertThatThrownBy(lambda).isInstanceOf(IncorrectStringException.class);
        for (int i = 0; i < 4; ++i) {
            session.tryToGuess("_");
        }
        GameState sessionCurrentState = session.currentState();
        int sessionBadAttempts = session.badAttempts();
        int sessionAccessibleAttempts = session.countAccessibleAttempts();
        boolean sessionUserCanInput = session.isPlayerCanInput();
        int sessionCountAvailableAttempts = session.countAvailableAttempts();

        // assert
        assertThat(sessionCurrentState).isEqualTo(GameState.LOST);
        assertThat(sessionBadAttempts).isEqualTo(numberOfAttempts);
        assertThat(sessionAccessibleAttempts).isEqualTo(numberOfAttempts);
        assertThat(sessionUserCanInput).isEqualTo(false);
        assertThat(sessionCountAvailableAttempts).isEqualTo(0);
    }
}
