package backend.academy.generators;

import backend.academy.maze.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PrimAlgoTest {
    @Test
    @DisplayName("Check Prim Algo (check that it works for square case)")
    void checkKruskalAlgoSquareTest() {
        // arrange
        MazeGenerator generator = new PrimGenerator();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(3, 3);
        int width = 4;
        int height = 4;

        // act
        var maze = generator.generate(height, width, start, end);

        // assert
        assertThat(maze.edges().size()).isNotZero();
        assertThat(maze.field()).isNotNull();
        assertThat(maze.getNumberOfVertices()).isEqualTo(width * height);
        assertThat(maze.width()).isEqualTo(width);
        assertThat(maze.height()).isEqualTo(height);
        assertThat(maze.getDistanceMatrix()).isNotNull();
    }

    @Test
    @DisplayName("Check Prim Algo (check that it works for rectangle case)")
    void checkKruskalAlgoRectTest() {
        // arrange
        MazeGenerator generator = new PrimGenerator();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(3, 3);
        int width = 4;
        int height = 5;

        // act
        var maze = generator.generate(height, width, start, end);

        // assert
        assertThat(maze.edges().size()).isNotZero();
        assertThat(maze.field()).isNotNull();
        assertThat(maze.getNumberOfVertices()).isEqualTo(width * height);
        assertThat(maze.width()).isEqualTo(width);
        assertThat(maze.height()).isEqualTo(height);
        assertThat(maze.getDistanceMatrix()).isNotNull();
    }
}
