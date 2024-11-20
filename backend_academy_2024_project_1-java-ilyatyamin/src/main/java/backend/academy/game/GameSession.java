package backend.academy.game;

import backend.academy.configs.ConfigParams;
import backend.academy.enums.Difficult;
import backend.academy.enums.GameState;
import backend.academy.wordstorage.Word;
import backend.academy.wordstorage.WordStorage;
import lombok.Getter;

public class GameSession {
    @Getter
    private GameState currentState;
    @Getter
    private final Word word;
    @Getter
    private int badAttempts = 0;
    private int totalAttempts = 0;
    private final ConfigParams params;

    public GameSession(Difficult difficult, String category, WordStorage storage, ConfigParams params) {
        this.currentState = GameState.CREATED;
        this.params = params;
        this.word = storage.getNotUsedWord(difficult, category);
        changeState();
    }

    /**
     * Process user input and open the letters if the user was right
     *
     * @param input user's input
     */
    public void tryToGuess(String input) {
        if (currentState == GameState.CREATED || currentState == GameState.PLAYING) {
            boolean result = word.tryToGuess(input);
            if (!result) {
                ++badAttempts;
            }
            ++totalAttempts;
            changeState();
        }
    }

    /**
     * Returns boolean if user can input other letters
     *
     * @return True if user has other attempts and word is not full solved, else False
     */
    public boolean isPlayerCanInput() {
        return badAttempts < params.numberAttempts() && word.countUnsolved() != 0;
    }

    /**
     * Returns the number of attempts that user can do at this moment
     *
     * @return int - the number of available attempts
     */
    public int countAvailableAttempts() {
        return params.numberAttempts() - badAttempts;
    }

    /**
     * Returns the number of attempts that user can do for all time
     * It was pointed in the config file
     *
     * @return int - the number of accessible attempts
     */
    public int countAccessibleAttempts() {
        return params.numberAttempts();
    }

    /**
     * Decides whether the user needs an attempt
     * QA: user needs attempts if a hint for this letter exists, and he used more than a half of accessible attempts
     *
     * @return True if a user needs a hint else False
     */
    public boolean isHintNeeded() {
        return word.hint() != null && badAttempts() > countAccessibleAttempts() / 2;
    }

    /**
     * Change the current state of the game session depends on the number of badAttempts
     */
    private void changeState() {
        if (totalAttempts == 0) {
            currentState = GameState.CREATED;
        } else if (word.countUnsolved() == 0) {
            currentState = GameState.WIN;
        } else if (badAttempts == params.numberAttempts() && word.countUnsolved() != 0) {
            currentState = GameState.LOST;
        } else {
            currentState = GameState.PLAYING;
        }
    }
}
