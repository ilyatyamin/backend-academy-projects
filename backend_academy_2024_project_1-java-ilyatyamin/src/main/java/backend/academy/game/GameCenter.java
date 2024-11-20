package backend.academy.game;

import backend.academy.configs.ConfigLoader;
import backend.academy.configs.ConfigParams;
import backend.academy.enums.Color;
import backend.academy.enums.Difficult;
import backend.academy.iohandlers.InputHandler;
import backend.academy.iohandlers.OutputHandler;
import backend.academy.wordstorage.StorageLoader;
import backend.academy.wordstorage.WordStorage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GameCenter {
    private final static String QUESTIONS_PATH = "questions.json";
    private final static String CONFIG_PATH = "config.json";

    private final InputHandler input;
    private final OutputHandler output;
    private final WordStorage storage;
    private final ConfigParams params;

    public GameCenter(
        InputHandler input,
        OutputHandler output
    ) throws IOException {
        this.input = input;
        this.output = output;

        storage = StorageLoader.loadFromStream(getStreamResources(QUESTIONS_PATH));
        params = ConfigLoader.loadFromStream(getStreamResources(CONFIG_PATH));
    }

    /**
     * Method for accessing to files that are locating in resources folder of the project
     *
     * @param name file name (aka, question.json, config.json, etc)
     * @return InputStream object for this file
     */
    private static InputStream getStreamResources(String name) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(name);
    }

    /**
     * Makes String template for a list of difficulties and their categories,
     * For example,
     * EASY: vegetables, fruits
     *
     * @param diffCategories map, there key is Difficult enum and value is Set of Strings that represents game topics
     * @return String template
     */
    private String categoriesToString(Map<Difficult, Set<String>> diffCategories) {
        StringBuilder sb = new StringBuilder();

        List<Difficult> categoriesInMap = new ArrayList<>(diffCategories.keySet());
        Collections.sort(categoriesInMap);

        for (var category : categoriesInMap) {
            // if difficult is empty (no categories), move to the next
            if (!diffCategories.get(category).isEmpty()) {
                sb.append(category.name()).append(":\t");
            } else {
                continue;
            }

            List<String> categoriesInSet = new ArrayList<>(diffCategories.get(category));
            // sort categories in alphabet order
            Collections.sort(categoriesInSet);
            for (int idx = 0; idx < categoriesInSet.size(); idx++) {
                sb.append(categoriesInSet.get(idx));
                if (idx != categoriesInSet.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Ask user some data for game creating using pointed InputStream
     * Choose random category and difficult if user wants that game do it
     * Creates the GameSession object using all information
     *
     * @return GameSession object
     * @throws IOException if there are some problems with input stream
     */
    public GameSession createGame() throws IOException {
        output.printLn("Игра в виселицу", Color.BLUE);

        // get a map of all categories that divided by difficult
        var diffCategories = storage.getAllCategories();
        output.printLn("Выберите сложность игры и категорию из предложенных:", Color.BLUE);
        output.print(categoriesToString(diffCategories));

        output.print(
            "Введите сложность (по-английски), или поставьте -, если хотите случайный выбор сложности и категории: ");

        // read difficult from console
        List<String> enumValues = Difficult.getStringPresentations();
        String strCategory = input.readStringUntil(
                s -> enumValues.contains(s.toUpperCase().strip()) || s.strip().equals("-"),
                "Неверный ввод сложности! Повторите ввод.\n")
            .toUpperCase();

        Difficult difficult;
        String category;
        if (!strCategory.equals("-")) {
            // case when user wants exact category and difficult
            difficult = Difficult.valueOf(strCategory);
            output.print("Введите категорию (как указано в тексте выше): ");
            category = input.readStringUntil(
                s -> diffCategories.get(difficult)
                    .contains(s.toLowerCase().strip()) || s.strip().equals("-"),
                "Неверный ввод категории! Проверьте ввод и повторите заново.\n");
        } else {
            // case when user wants to choose it randomly
            Random rnd = new Random();

            int sizeEnum = Difficult.values().length;
            difficult = Difficult.values()[rnd.nextInt(sizeEnum)];

            List<String> listOfCategories = new ArrayList<>(diffCategories.get(difficult));
            category = listOfCategories.get(rnd.nextInt(listOfCategories.size()));
        }

        return new GameSession(difficult, category, storage, params);
    }
}
