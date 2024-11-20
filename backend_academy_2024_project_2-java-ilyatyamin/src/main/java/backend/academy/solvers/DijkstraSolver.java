package backend.academy.solvers;

import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.maze.MazeCeil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class DijkstraSolver implements MazeSolver {
    @Override
    public List<Integer> solve(Maze maze, Coordinate start, Coordinate end) {
        // 1. get indexes of start and end
        int startIndex = this.findId(maze, start);
        int endIndex = this.findId(maze, end);

        // 2. Get distance matrix
        int[][] distanceMatrix = maze.getDistanceMatrix();

        // 2. Dijkstra algorithm
        int[] distances = new int[maze.width() * maze.height()];
        Arrays.fill(distances, Integer.MAX_VALUE);

        int[] path = new int[maze.width() * maze.height()];
        Arrays.fill(path, Integer.MAX_VALUE);

        distances[startIndex] = 0;
        path[startIndex] = -1;

        boolean[] visited = new boolean[maze.width() * maze.height()];
        int nowVisited = 0;
        int mustBeVisited = maze.width() * maze.height();

        while (nowVisited != mustBeVisited) {
            int minDistanceNode = getLowerDistanceNode(distances, visited);

            visited[minDistanceNode] = true;
            ++nowVisited;

            List<MazeCeil> ceils = getAdjacentList(maze.edges(), minDistanceNode);

            for (MazeCeil adjNode : ceils) {
                int adjNodeId = adjNode.id();
                if (!visited[adjNode.id()]
                    && distances[minDistanceNode] + distanceMatrix[minDistanceNode][adjNodeId] < distances[adjNodeId]) {
                    distances[adjNodeId] = distances[minDistanceNode] + distanceMatrix[minDistanceNode][adjNodeId];
                    path[adjNodeId] = minDistanceNode;
                }
            }
        }

        List<Integer> trace = new ArrayList<>();
        int next = endIndex;
        while (next != -1) {
            trace.add(next);
            if (next == Integer.MAX_VALUE) {
                return List.of();
            }
            next = path[next];
        }

        return trace.reversed();
    }

    private int getLowerDistanceNode(int[] distances, boolean[] visited) {
        return IntStream.range(0, distances.length)
            .filter(i -> !visited[i])
            .reduce((a, b) -> distances[a] <= distances[b] ? a : b)
            .orElse(-1);
    }
}
