package backend.academy.generators;

import backend.academy.maze.Coordinate;
import backend.academy.maze.GraphEdge;
import backend.academy.maze.Maze;
import backend.academy.maze.MazeCeil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class PrimGenerator implements MazeGenerator {
    private final Random random;

    public PrimGenerator() {
        this.random = new Random();
    }

    @Override
    public Maze generate(int height, int width, Coordinate start, Coordinate end) {
        Maze maze = new Maze(width, height, start, end);
        int numberOfCells = width * height;
        List<GraphEdge> edges = maze.edges();

        // 1. возьмем случайную вершину
        int nodeIndex = random.nextInt(width * height);
        MazeCeil startCell = maze.getCell(nodeIndex);

        // 2. Получим самое минимальное ребро
        var minEdgeFromStart = edges.stream()
            .filter(edge -> edge.begin() == startCell || edge.end() == startCell)
            .min(Comparator.comparing(GraphEdge::weight)).orElse(null);

        List<Integer> visited = new ArrayList<>();
        visited.add(nodeIndex);

        // 3. Будем класть ребра в MST (мин остовное дерево)
        List<GraphEdge> mst = new ArrayList<>();
        mst.add(minEdgeFromStart);

        // 4. Пока мы не обошли все вершины...
        while (visited.size() < numberOfCells) {
            GraphEdge minimalEdge = getEdgeWithMinimalWeight(edges, visited);
            mst.add(minimalEdge);
            // добавляем именно ту вершину, которой не было
            if (visited.contains(minimalEdge.begin().id())) {
                visited.add(minimalEdge.end().id());
            } else {
                visited.add(minimalEdge.begin().id());
            }

            // убираем стенку между ребрами
            minimalEdge.begin().removeWallWith(minimalEdge.end());
            minimalEdge.end().removeWallWith(minimalEdge.begin());
        }

        // обновляем ребра в лабиринте
        maze.edges(mst);

        return maze;
    }

    private GraphEdge getEdgeWithMinimalWeight(
        List<GraphEdge> edges,
        List<Integer> visited
    ) {
        var optional = edges.stream()
            .filter(edge -> (visited.contains(edge.begin().id()) && !visited.contains(edge.end().id()))
                || (!visited.contains(edge.begin().id()) && visited.contains(edge.end().id())))
            .min(Comparator.comparing(GraphEdge::weight));
        return optional.orElse(null);
    }
}
