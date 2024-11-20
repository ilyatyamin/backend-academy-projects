package backend.academy.solvers;

import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.maze.MazeCeil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AStarSolver implements MazeSolver {
    @Override
    public List<Integer> solve(Maze maze, Coordinate start, Coordinate end) {
        int[][] distanceMatrix = maze.getDistanceMatrix();
        int startIndex = this.findId(maze, start);
        int endIndex = this.findId(maze, end);

        Set<Integer> queue = new HashSet<>();
        List<Integer> visited = new ArrayList<>();

        queue.add(startIndex);

        int[] costs = new int[maze.getNumberOfVertices()];
        int[] euristics = new int[maze.getNumberOfVertices()];
        int[] path = new int[maze.getNumberOfVertices()];
        Arrays.fill(path, -1);

        costs[startIndex] = 0;
        path[startIndex] = Integer.MIN_VALUE;

        euristics[startIndex] = costs[startIndex] + maze.getCell(startIndex).distanceTo(maze.getCell(endIndex));

        while (!queue.isEmpty()) {
            int element = -1;
            int minFValue = Integer.MAX_VALUE;
            for (Integer value : queue) {
                if (euristics[value] < minFValue) {
                    minFValue = euristics[value];
                    element = value;
                }
            }
            queue.remove(element);

            if (element == endIndex) {
                break;
            }
            visited.add(element);

            List<MazeCeil> adjacencyList = getAdjacentList(maze.edges(), element);
            for (MazeCeil edge : adjacencyList) {
                int score = costs[element] + distanceMatrix[edge.id()][element];
                if (!visited.contains(edge.id()) || score < costs[edge.id()]) {
                    path[edge.id()] = element;
                    costs[edge.id()] = score;
                    euristics[edge.id()] =
                        costs[edge.id()] + maze.getCell(edge.id()).distanceTo(maze.getCell(endIndex));
                    queue.add(edge.id());
                }
            }

        }

        List<Integer> trace = new ArrayList<>();
        int next = endIndex;

        while (next != Integer.MIN_VALUE) {
            trace.add(next);
            if (next == -1) {
                return List.of();
            }
            next = path[next];
        }

        return trace.reversed();
    }
}
