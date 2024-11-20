package backend.academy;

import backend.academy.config.MazeLoader;
import backend.academy.generators.MazeGenerator;
import backend.academy.iohandlers.Color;
import backend.academy.iohandlers.InputHandler;
import backend.academy.iohandlers.OutputHandler;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.renderers.MazeRenderer;
import backend.academy.solvers.MazeSolver;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        // Потоки ввода и вывода
        OutputHandler outputStream = new OutputHandler(System.out);
        InputHandler inputStream = new InputHandler(new Scanner(System.in), outputStream);

        MazeLoader loader = new MazeLoader(inputStream, outputStream);

        int width = loader.readWidth();
        int height = loader.readHeight();

        loader.printGrid(width, height);

        Coordinate start = loader.readStartCoordinate(width, height);
        Coordinate end = loader.readEndCoordinate(width, height);

        MazeGenerator generator = loader.readGenerator();
        MazeSolver solver = loader.readPathMaker();
        MazeRenderer renderer = loader.readRenderStyle();

        // Генерируем лабиринт
        Maze generatedMaze = generator.generate(height, width, start, end);
        outputStream.printLn("Лабиринт без нахождения пути:", Color.GREEN);
        outputStream.printLn(renderer.render(generatedMaze));

        var path = solver.solve(generatedMaze, start, end);

        outputStream.printLn("Лабиринт с прорисованным путем:", Color.BLUE);
        outputStream.printLn(renderer.renderWithPath(generatedMaze, path));
    }
}
