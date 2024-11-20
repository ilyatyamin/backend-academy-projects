import backend.academy.enums.Difficult;
import backend.academy.exceptions.EmptyCategoryException;
import backend.academy.wordstorage.StorageLoader;
import backend.academy.wordstorage.Word;
import backend.academy.wordstorage.WordStorage;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Log4j2
public class WordStorageTest {
    private WordStorage storage;
    private final HashMap<Difficult, List<Word>> map;

    public WordStorageTest() {
        map = new HashMap<>();
        map.put(Difficult.EASY, new ArrayList<>());
        map.get(Difficult.EASY).add(new Word("AAA", "BBB", Difficult.EASY));
    }

    @BeforeEach
    void beforeEachTest() {
        storage = new WordStorage(map);
    }

    @Test
    void testSelfConstructibleStorage() {
        // act
        Word wordEasy = storage.getNotUsedWord(Difficult.EASY);
        Word wordEasyWithCategory1 = storage.getNotUsedWord(Difficult.EASY, "BBB");
        Word wordEasyWithCategory2 = storage.getNotUsedWord(Difficult.EASY, "bbB");

        // assert
        assertThat(wordEasy).isNotNull();
        assertThat(wordEasyWithCategory1).isNotNull();
        assertThat(wordEasyWithCategory2).isNotNull();
    }

    @Test
    void testNotExistedCategory() {
        // arrange, act
        ThrowableAssert.ThrowingCallable action = () -> storage.getNotUsedWord(Difficult.EASY, "unknown");

        // assert
        assertThatThrownBy(action).isInstanceOf(EmptyCategoryException.class);
    }

    @Test
    void testNotExistedDifficulty() {
        // arrange, act
        ThrowableAssert.ThrowingCallable action1 = () -> storage.getNotUsedWord(Difficult.HARD);
        ThrowableAssert.ThrowingCallable action2 = () -> storage.getNotUsedWord(Difficult.HARD, "unknown");

        // assert
        assertThatThrownBy(action1).isInstanceOf(EmptyCategoryException.class);
        assertThatThrownBy(action2).isInstanceOf(EmptyCategoryException.class);
    }

    @Test
    void testInitializeByFile() {
        // arrange, act
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        // assert
        try (InputStream is = classloader.getResourceAsStream("questions.json")) {
            assertDoesNotThrow(() -> StorageLoader.loadFromStream(is));
        } catch (IOException e) {
            // 0_0
        }
    }

    @Test
    void testInitializeByNotExistedFile() {
        // arrange, act
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        try (InputStream is = classloader.getResourceAsStream("NONEXISTEDFILE.STRANGESOURCE")) {
            ThrowableAssert.ThrowingCallable action = () -> StorageLoader.loadFromStream(is);

            // assert
            assertThatThrownBy(action).isInstanceOf(RuntimeException.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllCategories() {
        // arrange, act
        var categories = storage.getAllCategories();

        // assert
        assertThat(categories).isNotNull();
    }
}
