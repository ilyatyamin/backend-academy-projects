package backend.academy.solvers;

import backend.academy.maze.CeilType;
import backend.academy.maze.Coordinate;
import backend.academy.maze.GraphEdge;
import backend.academy.maze.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DijkstraSolverTest {
    @Test
    @DisplayName("Solve normal maze")
    void solveNormalMaze() {
        // arrange
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(2, 2);

        Maze maze = new Maze(3, 3, start, end);
        maze.edges(List.of(new GraphEdge(maze.field()[0][0], maze.field()[0][1], CeilType.STANDARD),
            new GraphEdge(maze.field()[0][1], maze.field()[0][2], CeilType.STANDARD),
            new GraphEdge(maze.field()[0][2], maze.field()[1][2], CeilType.STANDARD),
            new GraphEdge(maze.field()[1][2], maze.field()[2][2], CeilType.STANDARD)));
        MazeSolver solver = new DijkstraSolver();

        // act
        var path = solver.solve(maze, start, end);

        // assert
        assertThat(path.size()).isNotZero();
    }

    @Test
    @DisplayName("Solve not-solvable maze")
    void solveNotSolvableMaze() {
        // arrange
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(2, 2);

        Maze maze = new Maze(3, 3, start, end);
        maze.edges(List.of(new GraphEdge(maze.field()[0][0], maze.field()[0][1], CeilType.STANDARD),
            new GraphEdge(maze.field()[0][1], maze.field()[0][2], CeilType.STANDARD),
            new GraphEdge(maze.field()[1][2], maze.field()[2][2], CeilType.STANDARD)));
        MazeSolver solver = new DijkstraSolver();

        // act
        var path = solver.solve(maze, start, end);

        // assert
        assertThat(path.size()).isZero();
    }
}
