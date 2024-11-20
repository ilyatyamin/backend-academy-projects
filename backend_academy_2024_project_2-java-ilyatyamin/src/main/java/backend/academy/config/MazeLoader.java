package backend.academy.config;

import backend.academy.exceptions.NoSuchCoordinateException;
import backend.academy.generators.KruskalGenerator;
import backend.academy.generators.MazeGenerator;
import backend.academy.generators.PrimGenerator;
import backend.academy.iohandlers.Color;
import backend.academy.iohandlers.InputHandler;
import backend.academy.iohandlers.OutputHandler;
import backend.academy.maze.Coordinate;
import backend.academy.renderers.EmojiRenderer;
import backend.academy.renderers.MazeRenderer;
import backend.academy.renderers.TextRenderer;
import backend.academy.solvers.AStarSolver;
import backend.academy.solvers.DijkstraSolver;
import backend.academy.solvers.MazeSolver;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Predicate;
import lombok.SneakyThrows;
import org.apache.commons.math3.util.Pair;

public final class MazeLoader {
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final GenerationParams generationParams;
    private final Predicate<String> predicateToPassSizeCheck;

    private static final String WRONG_DATA_MESSAGE = "Проверьте Ваш ввод, пришли неверные данные. ";
    private static final String EXCEPTION_MESSAGE = "Произошла ошибка, повторите ввод. ";

    public MazeLoader(InputHandler inputHandler, OutputHandler outputHandler) {
        GenerationParamsLoader loader = new GenerationParamsLoader();

        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.generationParams = loader.loadFromConfigFile();

        this.predicateToPassSizeCheck = input -> {
            var isNumeric = InputHandler.isNumeric(input);
            if (!isNumeric) {
                return false;
            }

            int parsedInt = Integer.parseInt(input);
            return parsedInt >= generationParams.generationSizeLeftBorder()
                && parsedInt <= generationParams.generationSizeRightBorder();
        };
    }

    /**
     * Read maze width from console and validate it
     *
     * @return wight of maze
     */
    public int readWidth() {
        try {
            outputHandler.print(String.format("Введите ширину лабиринта (число от %d до %d): ",
                generationParams.generationSizeLeftBorder(), generationParams.generationSizeRightBorder()));
            return Integer.parseInt(this.inputHandler.readStringUntil(predicateToPassSizeCheck, WRONG_DATA_MESSAGE));
        } catch (Exception ex) {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
    }

    /**
     * Read maze height from console and validate it
     *
     * @return maze height
     */
    public int readHeight() {
        try {
            outputHandler.print(String.format("Введите высоту лабиринта (число от %d до %d): ",
                generationParams.generationSizeLeftBorder(), generationParams.generationSizeRightBorder()));
            return Integer.parseInt(this.inputHandler.readStringUntil(predicateToPassSizeCheck, WRONG_DATA_MESSAGE));
        } catch (Exception ex) {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
    }

    /**
     * Print maze grid to console (it needs when user mark start and end coordinates)
     */
    @SneakyThrows
    public void printGrid(int width, int height) {
        try {
            outputHandler.printLn("Перед вами сетка Вашего лабиринта: ");
            outputHandler.printLn(generateGrid(width, height));
        } catch (Exception ex) {
            outputHandler.printLn(ex.getMessage(), Color.RED);
        }
    }

    /**
     * Read coordinate of maze start from console and validate it
     *
     * @param width  maze width
     * @param height maze height
     * @return Coordinate object (start of maze)
     */
    public Coordinate readStartCoordinate(int width, int height) {
        boolean isInputed = false;
        int xCoordinate = -1;
        int yCoordinate = -1;

        while (!isInputed) {
            try {
                outputHandler.printLn(
                    "Введите координаты старта. Координата старта находится на краю лабиринта (или x = 0, или y = 0)");
                var pair = readCoordinates(width, height);
                xCoordinate = pair.getFirst();
                yCoordinate = pair.getSecond();

                if (xCoordinate != 0 && yCoordinate != 0) {
                    throw new NoSuchCoordinateException(
                        "Или координата x, или координата y должна быть равна нулю. Повторите ввод.");
                }

                isInputed = true;
            } catch (Exception e) {
                outputHandler.printLn(e.getMessage(), Color.RED);
            }
        }

        return new Coordinate(yCoordinate, xCoordinate);
    }

    /**
     * Read coordinate of the maze end from console and validate it
     *
     * @param width  maze width
     * @param height maze height
     * @return Coordinate object (start of maze)
     */
    public Coordinate readEndCoordinate(int width, int height) {
        boolean isInputed = false;
        int xCoordinate = -1;
        int yCoordinate = -1;

        while (!isInputed) {
            try {
                outputHandler.printLn(
                    "Введите координаты финиша. Финиш находится на краю лабиринта (или x=width-1, или y=height-1)");

                var pair = readCoordinates(width, height);
                xCoordinate = pair.getFirst();
                yCoordinate = pair.getSecond();

                if (xCoordinate != width - 1 && yCoordinate != height - 1) {
                    throw new NoSuchCoordinateException(
                        "Или координата x, или координата y должна быть на краю лабиринта. Повторите ввод.");
                }

                isInputed = true;
            } catch (Exception e) {
                outputHandler.printLn(e.getMessage(), Color.RED);
            }
        }

        return new Coordinate(yCoordinate, xCoordinate);
    }

    /**
     * Generate maze grid and returns it
     *
     * @param width  maze width
     * @param height maze height
     * @return String representation of maze
     */
    private String generateGrid(int width, int height) {
        StringBuilder grid = new StringBuilder();

        // разметка leftDim
        for (int i = 0; i < width; i++) {
            grid.append(" ");
            grid.append(i);
            grid.append(" ");
        }
        grid.append("\n");

        // сам leftDim
        grid.append(0);
        grid.append(".__".repeat(Math.max(0, width)));
        grid.append(">leftDim\n");

        // сетка по rightDim
        for (int i = 1; i < height; i++) {
            grid.append(i);
            grid.append("-\n");
        }
        grid.append(" rightDim\n");

        return grid.toString();
    }

    /**
     * Read user's choice about MazeGenerator from console
     *
     * @return MazeGenerator object (KruskalGenerator or PrimGenerator)
     */
    public MazeGenerator readGenerator() {
        outputHandler.print(
            "Введите 0, чтобы использовался алгоритм Краскала, иначе 1, если алгоритм Прима: ");
        int handler = Integer.parseInt(
            inputHandler.readStringUntil(input -> Objects.equals(input, "0") || Objects.equals(input, "1")));

        if (handler == 0) {
            return new KruskalGenerator();
        }
        return new PrimGenerator();
    }

    /**
     * Read user's choice about MazeSolver from console
     *
     * @return MazeSolver object (DijkstraSolver or AStarSolver)
     */
    public MazeSolver readPathMaker() {
        outputHandler.print(
            "Введите 0, если хотите, чтобы путь нашелся алгоритмом Дейкстры, иначе 1, если алгоритмом A*: ");
        int handler = Integer.parseInt(
            inputHandler.readStringUntil(input -> Objects.equals(input, "0") || Objects.equals(input, "1")));

        if (handler == 0) {
            return new DijkstraSolver();
        }
        return new AStarSolver();
    }

    /**
     * Read user's choice about MazeRenderer from console
     *
     * @return MazeRenderer object (EmojiRenderer or TextRenderer)
     */
    public MazeRenderer readRenderStyle() {
        outputHandler.print(
            "Введите 0, если хотите, чтобы лабиринт отрисовался в формате эмодзи (красивое), иначе 1, если текстом: ");
        int handler = Integer.parseInt(
            inputHandler.readStringUntil(input -> Objects.equals(input, "0") || Objects.equals(input, "1")));

        if (handler == 0) {
            return new EmojiRenderer();
        }
        return new TextRenderer();
    }

    private Pair<Integer, Integer> readCoordinates(int width, int height) {
        try {
            outputHandler.print(String.format("Введите координату leftDim (число от 0 до %s): ", width - 1));

            int xCoordinate = Integer.parseInt(
                this.inputHandler.readStringUntil(generatePredicateForCoordinate(width - 1), WRONG_DATA_MESSAGE));

            outputHandler.print(String.format("Введите координату rightDim (число от 0 до %s): ", height - 1));
            int yCoordinate = Integer.parseInt(
                this.inputHandler.readStringUntil(generatePredicateForCoordinate(height - 1), WRONG_DATA_MESSAGE));

            return new Pair<>(xCoordinate, yCoordinate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Predicate<String> generatePredicateForCoordinate(int value) {
        return input -> {
            var isNumeric = InputHandler.isNumeric(input);
            if (!isNumeric) {
                return false;
            }

            int parsedInt = Integer.parseInt(input);
            return parsedInt >= 0 && parsedInt <= value;
        };
    }
}
