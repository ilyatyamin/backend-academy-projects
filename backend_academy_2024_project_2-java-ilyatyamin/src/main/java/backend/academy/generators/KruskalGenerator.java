package backend.academy.generators;

import backend.academy.generators.containers.UnionFind;
import backend.academy.maze.Coordinate;
import backend.academy.maze.GraphEdge;
import backend.academy.maze.Maze;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KruskalGenerator implements MazeGenerator {
    @Override
    // time complexity: O(ElogE), E = |edges|
    public Maze generate(int height, int width,
                    Coordinate start, Coordinate end) {
        Maze maze = new Maze(width, height, start, end);

        // Мы реализуем алгоритм Краскала для поиска МСТ (минимального остовного дерева)
        // Его подробное описание можно почитать тут:
        // https://habr.com/ru/articles/569444/

        // Шаг 1. Сортируем ребра по неубыванию весов
        List<GraphEdge> edges = maze.edges();
        edges.sort(Comparator.comparingInt(GraphEdge::weight));

        // Шаг 2. Инициализирует СНМ (систему непересекающихся множеств), по факту Union Find
        UnionFind uf = new UnionFind(width * height);
        List<GraphEdge> path = new ArrayList<>();

        // Шаг 3. Проходимся по всем ребрам.
        // Проверяем, принадлежат ли инцидентные вершины текущего ребра разным подграфам с помощью UnionFind
        for (GraphEdge edge : edges) {
            int cellId1 = edge.begin().id();
            int cellId2 = edge.end().id();

            if (uf.find(cellId1) != uf.find(cellId2)) {
                uf.union(cellId1, cellId2);
                path.add(edge);

                // Убираем стену между ячейками
                edge.begin().removeWallWith(edge.end());
                edge.end().removeWallWith(edge.begin());
            }
        }

        // update edges in maze
        maze.edges(path);

        return maze;
    }
}
