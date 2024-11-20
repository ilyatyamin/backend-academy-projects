package backend.academy;

import backend.academy.enums.Color;
import backend.academy.game.GameCenter;
import backend.academy.game.GameInterface;
import backend.academy.game.GameSession;
import backend.academy.iohandlers.InputHandler;
import backend.academy.iohandlers.OutputHandler;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) throws IOException {
        OutputHandler output = new OutputHandler(System.out);
        InputHandler input = new InputHandler(new Scanner(System.in), output);

        String onlyRussianRegexPattern = "[а-яёА-ЯЁ]+";
        Pattern pattern = Pattern.compile(onlyRussianRegexPattern);

        GameCenter center;
        try {
            center = new GameCenter(input, output);
        } catch (Exception ex) {
            output.printLn("Ошибка при импорте файлов!", Color.RED);
            output.printLn("Проверьте, что файлы questions.json, config.json существуют!", Color.RED);
            output.printLn(ex.getMessage(), Color.RED);
            return;
        }

        GameSession session = center.createGame();
        GameInterface gameInterface = new GameInterface(output, session);

        while (session.isPlayerCanInput()) {
            gameInterface.printState();
            gameInterface.printInviteToLog();
            session.tryToGuess(input.readStringUntil(s -> s.length() == 1
                    && pattern.matcher(s).find(),
                "Проверьте, что длина вашего ввода -- 1 символ и введеный символ -- буква на русском языке. "));
        }

        gameInterface.printState();
    }
}
