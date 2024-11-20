package backend.academy.renderers;

import backend.academy.maze.CeilType;
import backend.academy.maze.Maze;
import backend.academy.maze.MazeCeil;
import backend.academy.maze.Wall;
import java.util.ArrayList;
import java.util.List;

public class EmojiRenderer implements MazeRenderer {
    private final static String WALL = "⬛";
    private final static String EMPTY = "⬜";
    private final static String PATH = "\uD83D\uDFE5";
    private final static String GRASS = "\uD83D\uDFE9";
    private final static String SWAMP = "\uD83D\uDFE6";
    private final static String START = "➡️";
    private final static String END = "❌";

    private final static Integer REPEAT_COUNTER = 3;

    @Override
    public String render(Maze maze) {
        List<StringBuilder> rows = new ArrayList<>(REPEAT_COUNTER * maze.height());

        for (int i = 0; i < REPEAT_COUNTER * maze.height(); i++) {
            rows.add(new StringBuilder());
        }

        for (int i = 0; i < maze.height(); ++i) {
            for (int j = 0; j < maze.width(); ++j) {
                String[] presentation = generateOneCeil(maze, i, j).split("\n");
                for (int k = 0; k < presentation.length; ++k) {
                    rows.get(REPEAT_COUNTER * i + k).append(presentation[k]);
                }
            }
        }

        StringBuilder presentation = new StringBuilder();
        for (StringBuilder row : rows) {
            presentation.append(row.toString()).append("\n");
        }

        return presentation.toString();
    }

    @Override
    public String renderWithPath(Maze maze, List<Integer> path) {
        List<StringBuilder> rows = new ArrayList<>(REPEAT_COUNTER * maze.height());

        for (int i = 0; i < REPEAT_COUNTER * maze.height(); i++) {
            rows.add(new StringBuilder());
        }

        for (int i = 0; i < maze.height(); ++i) {
            for (int j = 0; j < maze.width(); ++j) {
                int ceilId = maze.field()[i][j].id();

                String[] presentation;
                if (path.contains(ceilId)) {
                    presentation = generateOnePathCeil().split("\n");
                } else {
                    presentation = generateOneCeil(maze, i, j).split("\n");
                }

                for (int k = 0; k < presentation.length; ++k) {
                    rows.get(REPEAT_COUNTER * i + k).append(presentation[k]);
                }
            }
        }

        StringBuilder presentation = new StringBuilder();
        for (StringBuilder row : rows) {
            presentation.append(row.toString()).append("\n");
        }

        return presentation.toString();
    }

    private String generateOneCeil(Maze maze, int indexWidth, int indexHeight) {
        StringBuilder currentBlock = new StringBuilder();
        MazeCeil ceil = maze.field()[indexWidth][indexHeight];

        if (ceil.getWall(Wall.TOP)) {
            currentBlock.append(WALL.repeat(REPEAT_COUNTER)).append("\n");
        } else {
            currentBlock.append(WALL).append(EMPTY)
                .append(WALL).append("\n");
        }

        if (ceil.type() == CeilType.BEGIN) {
            currentBlock.append(START);
        } else if (ceil.type() == CeilType.END) {
            currentBlock.append(END);
        } else if (ceil.getWall(Wall.LEFT)) {
            currentBlock.append(WALL);
        } else {
            currentBlock.append(EMPTY);
        }

        switch (maze.field()[indexWidth][indexHeight].type()) {
            case STANDARD -> currentBlock.append(EMPTY);
            case GRASS -> currentBlock.append(GRASS);
            case SWAMP -> currentBlock.append(SWAMP);
            case BEGIN -> currentBlock.append(START);
            case END -> currentBlock.append(END);
            default -> throw new RuntimeException();
        }

        if (ceil.type() == CeilType.BEGIN) {
            currentBlock.append(START + "\n");
        } else if (ceil.type() == CeilType.END) {
            currentBlock.append(END + "\n");
        } else if (ceil.getWall(Wall.RIGHT)) {
            currentBlock.append(WALL + "\n");
        } else {
            currentBlock.append(EMPTY + "\n");
        }

        if (ceil.getWall(Wall.BOTTOM)) {
            currentBlock.append(WALL.repeat(REPEAT_COUNTER)).append("\n");
        } else {
            currentBlock.append(WALL).append(EMPTY)
                .append(WALL).append("\n");
        }

        return currentBlock.toString();
    }

    private String generateOnePathCeil() {
        return WALL + PATH + WALL + "\n"
            + PATH.repeat(REPEAT_COUNTER) + "\n"
            + WALL + PATH + WALL + "\n";
    }
}
