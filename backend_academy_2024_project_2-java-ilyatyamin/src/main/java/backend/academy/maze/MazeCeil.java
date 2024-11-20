package backend.academy.maze;

import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;

/**
 * Ячейка лабиринта
 */
public final class MazeCeil {
    @Getter
    private final int x;
    @Getter
    private final int y;
    @Getter
    private final int id;

    @Getter
    @Setter
    private CeilType type;

    // top = 0, left = 1, right = 2, bottom = 3
    private final Boolean[] walls;

    @SuppressWarnings("checkstyle:MagicNumber") MazeCeil(int x, int y, int id, CeilType type) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.type = type;

        this.walls = new Boolean[4];
        Arrays.fill(walls, true);
    }

    /**
     * Получает значение bool (стенка является стеной или проходом) в зависимости от расположения стены
     *
     * @param wall значение перечисления Wall
     * @return значение bool (стенка является стеной или проходом)
     */
    public Boolean getWall(Wall wall) {
        return walls[wall.value()];
    }

    /**
     * Убирает стену в зависимости от взаимного расположения другой ячейки
     *
     * @param anotherCeil другая ячейка лабиринта
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    public void removeWallWith(MazeCeil anotherCeil) {
        if (x == anotherCeil.x + 1) {
            // совпадение по leftDim вправо
            walls[1] = false;
            anotherCeil.walls[2] = false;
        } else if (x == anotherCeil.x - 1) {
            // совпадение по leftDim влево
            walls[2] = false;
            anotherCeil.walls[1] = false;
        } else if (y == anotherCeil.y - 1) {
            // совпадение по rightDim вверх
            walls[3] = false;
            anotherCeil.walls[0] = false;
        } else if (y == anotherCeil.y + 1) {
            // совпадение по rightDim вниз
            walls[0] = false;
            anotherCeil.walls[3] = false;
        }
    }

    public int distanceTo(MazeCeil anotherCeil) {
        // используется Манхеттенское расстояние
        return Math.abs(x - anotherCeil.x) + Math.abs(y - anotherCeil.y);
    }

}
