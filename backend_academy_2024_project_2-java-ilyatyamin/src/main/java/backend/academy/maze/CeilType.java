package backend.academy.maze;

import java.util.Random;
import lombok.Getter;

@Getter
public enum CeilType {
    GRASS(10),
    STANDARD(20),
    SWAMP(30),
    BEGIN(10),
    END(10);

    private final int value;
    private static final Random RANDOM = new Random();

    private static final int RANDOM_BORDER = 100;
    private static final int GRASS_BORDER = 15;
    private static final int SWAMP_BORDER = 85;

    CeilType(int value) {
        this.value = value;
    }

    public static CeilType generateRandom() {
        // Сделаем что-то вроде нормального распределения
        // генерируем число от 0 до 100.
        // если выпало 0-15 -- трава
        // если выпало 85-100 -- болото
        // иначе стандартная ячейка
        int value = RANDOM.nextInt(RANDOM_BORDER);

        if (value <= GRASS_BORDER) {
            return CeilType.GRASS;
        } else if (value >= SWAMP_BORDER) {
            return CeilType.SWAMP;
        } else {
            return CeilType.STANDARD;
        }
    }
}
