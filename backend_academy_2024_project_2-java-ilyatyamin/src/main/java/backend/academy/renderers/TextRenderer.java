package backend.academy.renderers;

import backend.academy.maze.Maze;
import backend.academy.maze.Wall;
import java.util.List;

public class TextRenderer implements MazeRenderer {

    @Override
    public String render(Maze maze) {
        return renderWithPath(maze, List.of());
    }

    @Override
    public String renderWithPath(Maze maze, List<Integer> path) {
        StringBuilder renderedMaze = new StringBuilder();
        var field = maze.field();

        for (int i = 0; i < maze.width(); i++) {
            for (int j = 0; j < maze.height(); j++) {
                renderedMaze.append("+");
                renderedMaze.append(field[i][j].getWall(Wall.TOP) ? "---" : "   ");
            }
            renderedMaze.append("+\n");

            for (int j = 0; j < maze.height(); j++) {
                renderedMaze.append(field[i][j].getWall(Wall.LEFT) ? "|" : " ");
                if (path.contains(field[i][j].id())) {
                    renderedMaze.append(" 0");
                } else {
                    renderedMaze.append("  ");
                }
            }
            renderedMaze.append("|\n");
        }

        // Печатаем нижние стены последней строки
        renderedMaze.append("+---".repeat(Math.max(0, maze.height())));
        renderedMaze.append("+");

        return renderedMaze.toString();
    }
}
