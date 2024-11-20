package backend.academy.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public final class Maze {
    @Getter
    private final int width;

    @Getter
    private final int height;

    @Getter
    private final MazeCeil[][] field;

    @Getter
    @Setter
    private List<GraphEdge> edges;

    private final Map<Integer, MazeCeil> cellsById;

    public Maze(int width, int height,
                Coordinate start, Coordinate end) {
        this.width = width;
        this.height = height;

        this.field = new MazeCeil[height][width];
        this.cellsById = new HashMap<>();

        int id = 0;
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                int currentId = id++;
                CeilType currentType = CeilType.generateRandom();

                this.field[j][i] = new MazeCeil(i, j, currentId, currentType);
                cellsById.put(currentId, this.field[j][i]);
            }
        }

        this.markAsType(start, CeilType.BEGIN);
        this.markAsType(end, CeilType.END);

        // fill edges
        this.edges = new ArrayList<>();
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                if (i + 1 < width) {
                    edges.add(new GraphEdge(field[j][i], field[j][i + 1],
                        field[j][i].type()));
                }
                if (j + 1 < height) {
                    edges.add(new GraphEdge(field[j][i], field[j + 1][i],
                        field[j][i].type()));
                }

            }
        }
    }

    public int[][] getDistanceMatrix() {
        int[][] distanceMatrix = new int[width * height][width * height];
        for (int[] row : distanceMatrix) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        for (GraphEdge edge : edges) {
            distanceMatrix[edge.begin().id()][edge.end().id()] = edge.begin().type().value();
            distanceMatrix[edge.end().id()][edge.begin().id()] = edge.begin().type().value();
        }

        return distanceMatrix;
    }

    public void markAsType(Coordinate point, CeilType type) {
        this.field[point.leftDim()][point.rightDim()] = this.field[point.leftDim()][point.rightDim()].type(type);
    }

    public MazeCeil getCell(int id) {
        return this.cellsById.get(id);
    }

    public int getNumberOfVertices() {
        return width * height;
    }
}
