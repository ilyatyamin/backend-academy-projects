package backend.academy.wordstorage;

import backend.academy.enums.Difficult;
import backend.academy.exceptions.EmptyCategoryException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class WordStorage {
    private final static String EXCEPTION_PHRASE = "Check that at least one word with this difficulty exists.";
    private final Map<Difficult, List<Word>> words;
    private final Random generator;

    public WordStorage(Map<Difficult, List<Word>> words) {
        this.words = words;
        this.generator = new Random();
    }

    /**
     * Generate random word from Storage with pointed Difficult
     *
     * @param diff Difficult enum value
     * @return Word object
     * @throws EmptyCategoryException if word with this configuration does not exist
     */
    public Word getNotUsedWord(Difficult diff) {
        List<Word> storage = words.get(diff);

        if (storage == null || storage.isEmpty()) {
            throw new EmptyCategoryException(EXCEPTION_PHRASE);
        }

        int index = generator.nextInt(storage.size());
        return storage.get(index);
    }

    /**
     * Generate random word from Storage with pointed Difficult and Category
     *
     * @param diff     Difficult enum value
     * @param category String value of the necessary category
     * @return Word object
     * @throws EmptyCategoryException if word with this configuration does not exist
     */
    public Word getNotUsedWord(Difficult diff, String category) {
        List<Word> storage = words.get(diff);
        if (storage == null) {
            throw new EmptyCategoryException(EXCEPTION_PHRASE);
        }

        Object[] storageCat = storage.stream().filter((Word word) ->
                (word.category().equals(category.toLowerCase())))
            .toArray();

        if (storageCat.length == 0) {
            throw new EmptyCategoryException("Check that at least one word with this difficulty and category exists.");
        }

        int index = generator.nextInt(storageCat.length);
        return (Word) storageCat[index];
    }

    /**
     * Get a map, where key is Difficult and key is set (!) of categories
     *
     * @return map, key = Difficult, value = HashSet
     */
    public Map<Difficult, Set<String>> getAllCategories() {
        Map<Difficult, Set<String>> categories = new HashMap<>();
        for (var key : words.keySet()) {
            categories.put(key, new HashSet<>());
            for (var word : words.get(key)) {
                categories.get(key).add(word.category());
            }
        }
        return categories;
    }
}
