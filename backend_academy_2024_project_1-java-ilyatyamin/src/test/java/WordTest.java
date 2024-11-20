import backend.academy.exceptions.IncorrectStringException;
import backend.academy.enums.Difficult;
import backend.academy.wordstorage.Word;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Log4j2
public class WordTest {
    @Test
    void testEmptyWord() {
        // arrange, act
        ThrowableAssert.ThrowingCallable lambda = () -> new Word("", "cat", Difficult.EASY);

        // assert
        assertThatThrownBy(lambda).isInstanceOf(IncorrectStringException.class);
    }

    @Test
    void testEmptyCategory() {
        // arrange, act
        ThrowableAssert.ThrowingCallable lambda = () -> new Word("name", "", Difficult.EASY);

        // assert
        assertThatThrownBy(lambda).isInstanceOf(IncorrectStringException.class);
    }

    @Test
    void testEmptyNormalWord() {
        // arrange
        Word word = new Word("Cucumber", "Vegetables", Difficult.MEDIUM);

        // act
        String wordPresentation = word.toString();

        // assert
        assertThat(wordPresentation).isEqualTo("________");
    }

    @Test
    void testWordSpecNullHint() {
        // arrange
        Word word = new Word("Cucumber", "Vegetables", Difficult.MEDIUM);

        // act
        String wordCategory = word.category();
        String wordHint = word.hint();
        Difficult wordDifficult = word.difficult();

        // assert
        assertThat(wordCategory).isEqualTo("Vegetables".toLowerCase());
        assertThat(wordHint).isEqualTo(null);
        assertThat(wordDifficult).isEqualTo(Difficult.MEDIUM);
    }

    @Test
    void testWordSpecEmptyHint() {
        // arrange
        Word word = new Word("Cucumber", "Vegetables", Difficult.MEDIUM, "");

        // act
        String wordCategory = word.category();
        String wordHint = word.hint();
        Difficult wordDifficult = word.difficult();

        // assert
        assertThat(wordCategory).isEqualTo("Vegetables".toLowerCase());
        assertThat(wordHint).isEqualTo(null);
        assertThat(wordDifficult).isEqualTo(Difficult.MEDIUM);
    }

    @Test
    void testWordSpecNotNullHint() {
        // arrange
        Word word = new Word("Cucumber", "Vegetables",
            Difficult.MEDIUM, "long vegetable");

        // act
        String wordCategory = word.category();
        String wordHint = word.hint();
        Difficult wordDifficult = word.difficult();

        // assert
        assertThat(wordCategory).isEqualTo("Vegetables".toLowerCase());
        assertThat(wordHint).isEqualTo("long vegetable");
        assertThat(wordDifficult).isEqualTo(Difficult.MEDIUM);
    }

    @Test
    void testTryToGuessIncorrectLen() {
        // arrange
        Word word = new Word("Абракадабра", "Странное", Difficult.HARD);

        // act
        ThrowableAssert.ThrowingCallable lambda = () -> word.tryToGuess("__");

        // assert
        assertThatThrownBy(lambda).isInstanceOf(IncorrectStringException.class);
    }

    @Test
    void testStandardGuessWord() {
        // arrange
        Word word = new Word("Абракадабра",
            "Странное", Difficult.HARD);

        // act
        boolean guessedA1 = word.tryToGuess("а");
        String guessedStrA1 = word.toString();
        int countUnsolvedA1 = word.countUnsolved();

        boolean guessedA2 = word.tryToGuess("ъ");
        String guessedStrA2 = word.toString();
        int countUnsolvedA2 = word.countUnsolved();

        boolean guessedA3 = word.tryToGuess("а");
        String guessedStrA3 = word.toString();
        int countUnsolvedA3 = word.countUnsolved();

        boolean guessedA4 = word.tryToGuess("б");
        String guessedStrA4 = word.toString();
        int countUnsolvedA4 = word.countUnsolved();

        word.tryToGuess("р");
        word.tryToGuess("д");
        word.tryToGuess("к");
        String finalView = word.toString();
        int finalUnsolved = word.countUnsolved();

        // assert
        assertThat(guessedA1).isEqualTo(true);
        assertThat(guessedStrA1).isEqualTo("а__а_а_а__а");
        assertThat(countUnsolvedA1).isEqualTo(6);

        assertThat(guessedA2).isEqualTo(false); //
        assertThat(guessedStrA2).isEqualTo("а__а_а_а__а");
        assertThat(countUnsolvedA2).isEqualTo(6);

        assertThat(guessedA3).isEqualTo(true);
        assertThat(guessedStrA3).isEqualTo("а__а_а_а__а");
        assertThat(countUnsolvedA3).isEqualTo(6);

        assertThat(guessedA4).isEqualTo(true);
        assertThat(guessedStrA4).isEqualTo("аб_а_а_аб_а");
        assertThat(countUnsolvedA4).isEqualTo(4);

        assertThat(finalView).isEqualTo("абракадабра");
        assertThat(finalUnsolved).isEqualTo(0);
    }
}
