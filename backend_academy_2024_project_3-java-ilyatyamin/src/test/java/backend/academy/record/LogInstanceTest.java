package backend.academy.record;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LogInstanceTest {
    @Test
    @DisplayName("checks isReleasedLaterThan function (true)")
    void isReleasedLaterThanTestTrue() {
        // arrange
        LocalDateTime instanceTime = LocalDateTime.of(2020, 9, 8, 12, 10);
        LogInstance instance = LogInstance.builder().timeLocal(instanceTime).build();

        LocalDate expectedTime = LocalDate.of(2020, 9, 7);

        // act
        boolean isLaterThan = instance.isReleasedLaterThan(expectedTime);

        // assert
        assertThat(isLaterThan).isTrue();
    }

    @Test
    @DisplayName("checks isReleasedLaterThan function (false)")
    void isReleasedLaterThanTestFalse() {
        // arrange
        LocalDateTime instanceTime = LocalDateTime.of(2020, 9, 8, 12, 10);
        LogInstance instance = LogInstance.builder().timeLocal(instanceTime).build();

        LocalDate expectedTime = LocalDate.of(2020, 9, 10);

        // act
        boolean isLaterThan = instance.isReleasedLaterThan(expectedTime);

        // assert
        assertThat(isLaterThan).isFalse();
    }

    @Test
    @DisplayName("checks isReleasedLaterThan function (equals)")
    void isReleasedLaterThanTestTrue2() {
        // arrange
        LocalDateTime instanceTime = LocalDateTime.of(2020, 9, 8, 12, 10);
        LogInstance instance = LogInstance.builder().timeLocal(instanceTime).build();

        LocalDate expectedTime = LocalDate.of(2020, 9, 8);

        // act
        boolean isLaterThan = instance.isReleasedLaterThan(expectedTime);

        // assert
        assertThat(isLaterThan).isTrue();
    }

    @Test
    @DisplayName("checks isReleasedEarlierThan function (true)")
    void isReleasedEarlierThanTest1() {
        // arrange
        LocalDateTime instanceTime = LocalDateTime.of(2020, 9, 8, 12, 10);
        LogInstance instance = LogInstance.builder().timeLocal(instanceTime).build();

        LocalDate expectedTime = LocalDate.of(2020, 10, 7);

        // act
        boolean isLaterThan = instance.isReleasedEarlierThan(expectedTime);

        // assert
        assertThat(isLaterThan).isTrue();
    }

    @Test
    @DisplayName("checks isReleasedEarlierThan function (false)")
    void isReleasedEarlierThanTest2() {
        // arrange
        LocalDateTime instanceTime = LocalDateTime.of(2020, 9, 8, 12, 10);
        LogInstance instance = LogInstance.builder().timeLocal(instanceTime).build();

        LocalDate expectedTime = LocalDate.of(2020, 8, 7);

        // act
        boolean isLaterThan = instance.isReleasedEarlierThan(expectedTime);

        // assert
        assertThat(isLaterThan).isFalse();
    }

    @Test
    @DisplayName("checks isReleasedEarlierThan function (true, equals)")
    void isReleasedEarlierThanTest3() {
        // arrange
        LocalDateTime instanceTime = LocalDateTime.of(2020, 9, 8, 12, 10);
        LogInstance instance = LogInstance.builder().timeLocal(instanceTime).build();

        LocalDate expectedTime = LocalDate.of(2020, 9, 8);

        // act
        boolean isLaterThan = instance.isReleasedEarlierThan(expectedTime);

        // assert
        assertThat(isLaterThan).isTrue();
    }
}
