package backend.academy.generators;

import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;

public interface MazeGenerator {
    Maze generate(
        int height, int width,
        Coordinate start, Coordinate end
    );
}
