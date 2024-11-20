package backend.academy.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PathChecker {
    public static boolean isPathFromNetwork(String path) {
        return path.contains("://");
    }
}
