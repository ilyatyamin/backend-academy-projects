package backend.academy.config;

import backend.academy.generators.KruskalGenerator;
import backend.academy.generators.PrimGenerator;
import backend.academy.iohandlers.InputHandler;
import backend.academy.iohandlers.OutputHandler;
import backend.academy.maze.Coordinate;
import backend.academy.renderers.EmojiRenderer;
import backend.academy.renderers.TextRenderer;
import backend.academy.solvers.AStarSolver;
import backend.academy.solvers.DijkstraSolver;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MazeLoaderTest {
    @Test
    @DisplayName("input correct width test")
    void inputCorrectWidthTest() {
        // arrange
        String userInput = "42";
        OutputHandler output = Mockito.mock(OutputHandler.class);

        InputHandler handler = new InputHandler(
            new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8))),
            output
        );

        MazeLoader loader = new MazeLoader(handler, output);

        // act
        int value = loader.readWidth();

        // assert
        assertThat(value).isEqualTo(42);
    }

    @Test
    @DisplayName("input correct height test")
    void inputCorrectHeightTest() {
        // arrange
        String userInput = "42";
        OutputHandler output = Mockito.mock(OutputHandler.class);

        InputHandler handler = new InputHandler(
            new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8))),
            output
        );

        MazeLoader loader = new MazeLoader(handler, output);

        // act
        int value = loader.readHeight();

        // assert
        assertThat(value).isEqualTo(42);
    }

    @Test
    @DisplayName("input incorrect width test")
    void inputIncorrectWidthTest() {
        // arrange
        String userInput = "strange string\n-1\n10";
        OutputHandler output = Mockito.mock(OutputHandler.class);

        InputHandler handler = new InputHandler(
            new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8))),
            output
        );

        MazeLoader loader = new MazeLoader(handler, output);

        // act
        int value = loader.readWidth();

        // assert
        assertThat(value).isEqualTo(10);
    }

    @Test
    @DisplayName("input incorrect height test")
    void inputIncorrectHeightTest() {
        // arrange
        String userInput = "strange string\n-1\n10";
        OutputHandler output = Mockito.mock(OutputHandler.class);

        InputHandler handler = new InputHandler(
            new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8))),
            output
        );

        MazeLoader loader = new MazeLoader(handler, output);

        // act
        int value = loader.readHeight();

        // assert
        assertThat(value).isEqualTo(10);
    }

    @Test
    @DisplayName("input correct start coordinate")
    void inputCorrectStartCoordinateTest() {
        // arrange
        String userInput = "strange string\n-1\n5\nanoit\n8\n0\n0";
        // (сначала ввели неверные координаты, потом не int-ы, потом норм координаты
        OutputHandler output = Mockito.mock(OutputHandler.class);

        InputHandler handler = new InputHandler(
            new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8))),
            output
        );

        MazeLoader loader = new MazeLoader(handler, output);

        // act
        Coordinate value = loader.readStartCoordinate(10, 10);

        // assert
        assertThat(value.leftDim()).isEqualTo(0);
        assertThat(value.rightDim()).isEqualTo(0);
    }

    @Test
    @DisplayName("input correct end coordinate")
    void inputCorrectEndCoordinateTest() {
        // arrange
        String userInput = "strange string\n-1\n5\nanoit\n8\n99\n99";
        // (сначала ввели неверные координаты, потом не int-ы, потом норм координаты
        OutputHandler output = Mockito.mock(OutputHandler.class);

        InputHandler handler = new InputHandler(
            new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8))),
            output
        );

        MazeLoader loader = new MazeLoader(handler, output);

        // act
        Coordinate value = loader.readEndCoordinate(100, 100);

        // assert
        assertThat(value.leftDim()).isEqualTo(99);
        assertThat(value.rightDim()).isEqualTo(99);
    }

    @Test
    @DisplayName("input generator choice (kruskal)")
    void inputGeneratorChoiceTest1() {
        // arrange
        String userInput = "0";
        // (сначала ввели неверные координаты, потом не int-ы, потом норм координаты
        OutputHandler output = Mockito.mock(OutputHandler.class);

        InputHandler handler = new InputHandler(
            new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8))),
            output
        );

        MazeLoader loader = new MazeLoader(handler, output);

        // act
        var value = loader.readGenerator();

        // assert
        assertThat(value).isInstanceOf(KruskalGenerator.class);
    }

    @Test
    @DisplayName("input generator choice (prim)")
    void inputGeneratorChoiceTest2() {
        // arrange
        String userInput = "1";
        // (сначала ввели неверные координаты, потом не int-ы, потом норм координаты
        OutputHandler output = Mockito.mock(OutputHandler.class);

        InputHandler handler = new InputHandler(
            new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8))),
            output
        );

        MazeLoader loader = new MazeLoader(handler, output);

        // act
        var value = loader.readGenerator();

        // assert
        assertThat(value).isInstanceOf(PrimGenerator.class);
    }

    @Test
    @DisplayName("input incorrect generator choice (prim)")
    void inputGeneratorChoiceIncorrectTest() {
        // arrange
        String userInput = "15\n1";
        // (сначала ввел неверную цифру, потом верную)
        OutputHandler output = Mockito.mock(OutputHandler.class);

        InputHandler handler = new InputHandler(
            new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8))),
            output
        );

        MazeLoader loader = new MazeLoader(handler, output);

        // act
        var value = loader.readGenerator();

        // assert
        assertThat(value).isInstanceOf(PrimGenerator.class);
    }

    @Test
    @DisplayName("input correct path maker (Dijkstra)")
    void inputCorrectPathMakerTest1() {
        // arrange
        String userInput = "0";
        // (сначала ввел неверную цифру, потом верную)
        OutputHandler output = Mockito.mock(OutputHandler.class);

        InputHandler handler = new InputHandler(
            new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8))),
            output
        );

        MazeLoader loader = new MazeLoader(handler, output);

        // act
        var value = loader.readPathMaker();

        // assert
        assertThat(value).isInstanceOf(DijkstraSolver.class);
    }

    @Test
    @DisplayName("input correct path maker (A*)")
    void inputCorrectPathMakerTest2() {
        // arrange
        String userInput = "1";
        // (сначала ввел неверную цифру, потом верную)
        OutputHandler output = Mockito.mock(OutputHandler.class);

        InputHandler handler = new InputHandler(
            new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8))),
            output
        );

        MazeLoader loader = new MazeLoader(handler, output);

        // act
        var value = loader.readPathMaker();

        // assert
        assertThat(value).isInstanceOf(AStarSolver.class);
    }

    @Test
    @DisplayName("input correct render style")
    void inputRenderStyleTest1() {
        // arrange
        String userInput = "0";
        // (сначала ввел неверную цифру, потом верную)
        OutputHandler output = Mockito.mock(OutputHandler.class);

        InputHandler handler = new InputHandler(
            new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8))),
            output
        );

        MazeLoader loader = new MazeLoader(handler, output);

        // act
        var value = loader.readRenderStyle();

        // assert
        assertThat(value).isInstanceOf(EmojiRenderer.class);
    }

    @Test
    @DisplayName("input correct render style (text)")
    void inputRenderStyleTest2() {
        // arrange
        String userInput = "1";
        // (сначала ввел неверную цифру, потом верную)
        OutputHandler output = Mockito.mock(OutputHandler.class);

        InputHandler handler = new InputHandler(
            new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8))),
            output
        );

        MazeLoader loader = new MazeLoader(handler, output);

        // act
        var value = loader.readRenderStyle();

        // assert
        assertThat(value).isInstanceOf(TextRenderer.class);
    }

    @Test
    @DisplayName("Print Grid Test")
    @SneakyThrows
    void printGridTest() {
        // arrange
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String utf8 = StandardCharsets.UTF_8.name();

        OutputHandler output = new OutputHandler(new PrintStream(baos, true, utf8));
        InputHandler handler = Mockito.mock(InputHandler.class);
        MazeLoader loader = new MazeLoader(handler, output);

        int width = 10;
        int height = 10;

        // act
        loader.printGrid(width, height);

        // assert
        assertThat(baos.toString().length()).isPositive();
    }
}
