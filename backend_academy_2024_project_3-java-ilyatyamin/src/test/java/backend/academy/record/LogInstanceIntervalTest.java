package backend.academy.record;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LogInstanceIntervalTest {
    @Test
    @DisplayName("00:00 -- 03:00, not borders")
    void firstTimeIntervalTest1() {
        // arrange
        LocalDateTime instanceTime = LocalDateTime.of(2020, 9, 8, 1, 0);
        LogInstance instance = LogInstance.builder().timeLocal(instanceTime).build();

        // act
        String timeInterval = instance.getTimeInterval();

        // assert
        assertThat(timeInterval).isEqualTo("00:00 -- 03:00");
    }

    @Test
    @DisplayName("00:00 -- 03:00, left border")
    void firstTimeIntervalTest2() {
        // arrange
        LocalDateTime instanceTime = LocalDateTime.of(2020, 9, 8, 0, 0);
        LogInstance instance = LogInstance.builder().timeLocal(instanceTime).build();

        // act
        String timeInterval = instance.getTimeInterval();

        // assert
        assertThat(timeInterval).isEqualTo("00:00 -- 03:00");
    }

    @Test
    @DisplayName("00:00 -- 03:00, right border")
    void firstTimeIntervalTest3() {
        // arrange
        LocalDateTime instanceTime = LocalDateTime.of(2020, 9, 8, 3, 0);
        LogInstance instance = LogInstance.builder().timeLocal(instanceTime).build();

        // act
        String timeInterval = instance.getTimeInterval();

        // assert
        assertThat(timeInterval).isEqualTo("03:00 -- 06:00");
    }

    @Test
    @DisplayName("12:00 -- 15:00, not borders")
    void firstTimeIntervalTest4() {
        // arrange
        LocalDateTime instanceTime = LocalDateTime.of(2020, 9, 8, 12, 1);
        LogInstance instance = LogInstance.builder().timeLocal(instanceTime).build();

        // act
        String timeInterval = instance.getTimeInterval();

        // assert
        assertThat(timeInterval).isEqualTo("12:00 -- 15:00");
    }

    @Test
    @DisplayName("12:00 -- 15:00, left border")
    void firstTimeIntervalTest5() {
        // arrange
        LocalDateTime instanceTime = LocalDateTime.of(2020, 9, 8, 12, 0);
        LogInstance instance = LogInstance.builder().timeLocal(instanceTime).build();

        // act
        String timeInterval = instance.getTimeInterval();

        // assert
        assertThat(timeInterval).isEqualTo("12:00 -- 15:00");
    }

    @Test
    @DisplayName("12:00 -- 15:00, right border")
    void firstTimeIntervalTest6() {
        // arrange
        LocalDateTime instanceTime = LocalDateTime.of(2020, 9, 8, 15, 0);
        LogInstance instance = LogInstance.builder().timeLocal(instanceTime).build();

        // act
        String timeInterval = instance.getTimeInterval();

        // assert
        assertThat(timeInterval).isEqualTo("15:00 -- 18:00");
    }

    @Test
    @DisplayName("21:00 -- 23:59, not borders")
    void firstTimeIntervalTest7() {
        // arrange
        LocalDateTime instanceTime = LocalDateTime.of(2020, 9, 8, 22, 54);
        LogInstance instance = LogInstance.builder().timeLocal(instanceTime).build();

        // act
        String timeInterval = instance.getTimeInterval();

        // assert
        assertThat(timeInterval).isEqualTo("21:00 -- 23:59");
    }

    @Test
    @DisplayName("21:00 -- 23:59, left border")
    void firstTimeIntervalTest8() {
        // arrange
        LocalDateTime instanceTime = LocalDateTime.of(2020, 9, 8, 21, 0);
        LogInstance instance = LogInstance.builder().timeLocal(instanceTime).build();

        // act
        String timeInterval = instance.getTimeInterval();

        // assert
        assertThat(timeInterval).isEqualTo("21:00 -- 23:59");
    }

    @Test
    @DisplayName("21:00 -- 23:59, right border")
    void firstTimeIntervalTest9() {
        // arrange
        LocalDateTime instanceTime = LocalDateTime.of(2020, 9, 8, 23, 59);
        LogInstance instance = LogInstance.builder().timeLocal(instanceTime).build();

        // act
        String timeInterval = instance.getTimeInterval();

        // assert
        assertThat(timeInterval).isEqualTo("21:00 -- 23:59");
    }
}
