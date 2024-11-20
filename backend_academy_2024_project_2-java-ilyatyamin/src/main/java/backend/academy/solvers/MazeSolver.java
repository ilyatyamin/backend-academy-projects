package backend.academy.solvers;

import backend.academy.exceptions.NoSuchCoordinateException;
import backend.academy.maze.Coordinate;
import backend.academy.maze.GraphEdge;
import backend.academy.maze.Maze;
import backend.academy.maze.MazeCeil;
import java.util.Arrays;
import java.util.List;

public interface MazeSolver {
    List<Integer> solve(Maze maze, Coordinate start, Coordinate end);

    default int findId(Maze maze, Coordinate coord) {
        var optional = Arrays.stream(maze.field())
            .flatMap(Arrays::stream)
            .filter(ceil -> ceil.x() == coord.rightDim() && ceil.y() == coord.leftDim())
            .findFirst();
        if (optional.isPresent()) {
            return optional.get().id();
        } else {
            throw new NoSuchCoordinateException("Coordinate with this leftDim and rightDim not found in graph.");
        }
    }

    default List<MazeCeil> getAdjacentList(List<GraphEdge> edges, int ceilId) {
        return edges.stream()
            .filter(edge -> edge.begin().id() == ceilId || edge.end().id() == ceilId)
            .filter(edge -> edge.weight() != Integer.MAX_VALUE)
            .map(edge -> edge.begin().id() == ceilId ? edge.end() : edge.begin())
            .toList();
    }
}
