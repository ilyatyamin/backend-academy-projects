package backend.academy.renderers;

import backend.academy.maze.Maze;
import java.util.List;

public interface MazeRenderer {
    String render(Maze maze);

    String renderWithPath(Maze maze, List<Integer> path);
}
