package backend.academy.maze;

import java.util.Random;
import lombok.Getter;

@Getter
public class GraphEdge {
    private final MazeCeil begin;
    private final MazeCeil end;
    private final CeilType type;

    private final int weight = RANDOMIZER.nextInt(100);
    private final static Random RANDOMIZER = new Random();

    public GraphEdge(MazeCeil begin, MazeCeil end, CeilType type) {
        this.begin = begin;
        this.end = end;
        this.type = type;
    }
}
