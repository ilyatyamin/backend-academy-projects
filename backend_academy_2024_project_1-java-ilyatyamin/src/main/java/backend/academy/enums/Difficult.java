package backend.academy.enums;

import java.util.Arrays;
import java.util.List;

public enum Difficult {
    EASY,
    MEDIUM,
    HARD;

    /**
     * Returns list of String objects of Difficult, aka [Easy, Medium...]
     * @return List of Difficult
     */
    public static List<String> getStringPresentations() {
        return Arrays.stream(Arrays.stream(Difficult.values()).map(Enum::name)
            .toArray(String[]::new)).toList();
    }
}
