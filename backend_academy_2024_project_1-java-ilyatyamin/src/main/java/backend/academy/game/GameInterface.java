package backend.academy.game;

import backend.academy.enums.Color;
import backend.academy.iohandlers.OutputHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInterface {
    private final OutputHandler output;
    private final GameSession session;
    private final Map<Integer, List<Integer>> statesPerCount;

    private final static String HAPPY_MAN = """
                       _.--""--._
                    .'          `.
                   /   O      O   \\
                  |    \\  ^  /    |
                  \\    `---'    /
                   `.________.'
                      |  |  |
                      |  |  |
                      |  |  |
                      |  |  |
                      |  |  |
                      |  |  |
                      |_|  |_|
                       `-'
        """;

    private final static String SAD_MAN = """
            _.--""--._
          .'     `.
          /  _   _  \\
         |  / \\ / \\  |
         \\  `---'  /
          `.________.'
           | | |
           | | |
           | | |
           | | |
           | | |
           | | |
           |_| |_|
        """;

    public GameInterface(
        OutputHandler handler,
        GameSession session
    ) {
        this.output = handler;
        this.session = session;

        statesPerCount = new HashMap<>();
        fillMap();
    }

    @SuppressWarnings({"this isn't the magic number, it's sequence of showing state depends on number of attempts",
        "checkstyle:MagicNumber"})
    private void fillMap() {
        // this method fill map, where key is the number of attempts,
        // value is the array of necessary states of Hangman
        statesPerCount.put(1, new ArrayList<>(List.of(8)));
        statesPerCount.put(2, new ArrayList<>(List.of(0, 8)));
        statesPerCount.put(3, new ArrayList<>(List.of(0, 4, 8)));
        statesPerCount.put(4, new ArrayList<>(List.of(0, 2, 5, 8)));
        statesPerCount.put(5, new ArrayList<>(List.of(0, 2, 4, 6, 8)));
        statesPerCount.put(6, new ArrayList<>(List.of(0, 1, 2, 4, 6, 8)));
        statesPerCount.put(7, new ArrayList<>(List.of(0, 1, 2, 4, 6, 7, 8)));
        statesPerCount.put(8, new ArrayList<>(List.of(0, 1, 2, 3, 4, 6, 7, 8)));
        statesPerCount.put(9, new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8)));
    }

    public void printState() throws IOException {
        output.clear();
        output.printLn("");

        switch (session.currentState()) {
            case WIN -> {
                output.printLn(HAPPY_MAN);
                output.printLn(String.format("Вы победили! Ваше слово: %s", session.word()),
                    Color.GREEN);
            }
            case LOST -> {
                output.printLn(SAD_MAN);
                output.printLn("Вы проиграли :( приходите еще!",
                    Color.RED);
            }
            case PLAYING, CREATED -> {
                output.printLn(getWordInfo(), Color.BLUE);

                output.printLn(String.format("Слово: %s", session.word().toString()));
                List<Integer> pictures = statesPerCount.get(session.countAccessibleAttempts());

                if (session.isHintNeeded()) {
                    output.printLn(String.format("Подсказка к слову: %s", session.word().hint()));
                }

                HangmanState state = HangmanState.values()[pictures.get(session.badAttempts())];
                output.printLn(state.state());
            }
            default -> {
            }
        }
    }

    public void printInviteToLog() throws IOException {
        output.print("Ваш ввод: ");
    }

    private String getWordInfo() {
        return String.format(
            "Сложность: %s\t\tКатегория: %s\t\tОсталось попыток:%x",
            session.word().difficult(),
            session.word().category(),
            session.countAvailableAttempts());
    }
}
