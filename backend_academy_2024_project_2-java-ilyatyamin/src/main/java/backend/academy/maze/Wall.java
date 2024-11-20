package backend.academy.maze;

import lombok.Getter;

@Getter
public enum Wall {
    TOP(0),
    LEFT(1),
    RIGHT(2),
    BOTTOM(3);

    private final int value;

    Wall(int value) {
        this.value = value;
    }
}
