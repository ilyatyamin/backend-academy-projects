package backend.academy.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PathCheckerTest {
    @Test
    @DisplayName("valid URL test")
    void validURLTest() {
        // arrange
        String url = "https://edu.tbank.ru/my-activities/courses/";

        // act
        boolean check = PathChecker.isPathFromNetwork(url);

        // assert
        assertThat(check).isEqualTo(true);
    }

    @Test
    @DisplayName("valid local path test")
    void validPathTest() {
        // arrange
        String url = "localdata/2024/**";

        // act
        boolean check = PathChecker.isPathFromNetwork(url);

        // assert
        assertThat(check).isEqualTo(false);
    }
}
