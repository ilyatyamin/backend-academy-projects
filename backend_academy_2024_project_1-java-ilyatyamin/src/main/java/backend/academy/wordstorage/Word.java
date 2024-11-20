package backend.academy.wordstorage;

import backend.academy.enums.Difficult;
import backend.academy.exceptions.IncorrectStringException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class Word {
    private final String name;
    @Getter private final String category;
    @Getter private final Difficult difficult;
    @Getter private final String hint;
    private final boolean[] guessedLetters;

    public Word(
        String name,
        String category,
        Difficult difficult
    ) {
        this(name, category, difficult, null);
    }

    @JsonCreator
    public Word(
        @JsonProperty("name") String name,
        @JsonProperty("category") String category,
        @JsonProperty("difficult") Difficult difficult,
        @JsonProperty("hint") String hint
    ) {
        if (name.isEmpty()) {
            throw new IncorrectStringException("Name cannot be empty");
        }
        if (category.isEmpty()) {
            throw new IncorrectStringException("Category cannot be empty");
        }

        this.name = name.toLowerCase().strip();
        this.category = category.toLowerCase().strip();
        this.difficult = difficult;
        if (hint == null || hint.isEmpty()) {
            this.hint = null;
        } else {
            this.hint = hint;
        }
        this.guessedLetters = new boolean[this.name.length()];
    }

    /**
     * Count unsolved letters in the word
     *
     * @return number of unsolved letters
     */
    public int countUnsolved() {
        int count = 0;
        for (boolean letter : guessedLetters) {
            if (!letter) {
                count++;
            }
        }
        return count;
    }

    /**
     * Open solved letters in the word
     *
     * @param input - user input
     * @return True if any letters were opened in this round else False
     */
    public boolean tryToGuess(String input) {
        if (input.length() != 1) {
            throw new IncorrectStringException(
                String.format("Expected that len of string is 1, got %d", input.length()));
        }
        char letter = Character.toLowerCase(input.charAt(0));
        boolean result = false;

        for (int i = 0; i < guessedLetters.length; ++i) {
            if (Character.toLowerCase(this.name.charAt(i)) == letter) {
                guessedLetters[i] = true;
                result = true;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < guessedLetters.length; ++i) {
            if (!guessedLetters[i]) {
                sb.append("_");
            } else {
                sb.append(this.name.charAt(i));
            }
        }
        return sb.toString();
    }
}
