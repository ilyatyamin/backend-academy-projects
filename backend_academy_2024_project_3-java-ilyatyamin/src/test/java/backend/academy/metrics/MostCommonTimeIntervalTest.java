package backend.academy.metrics;

import backend.academy.record.LogInstance;
import backend.academy.report.LogReport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MostCommonTimeIntervalTest {
    @Test
    @DisplayName("standard test 1")
    void standardTest1() {
        // arrange
        MetricCounter counter = new MostCommonTimeIntervalCounter();
        LogReport report = new LogReport();

        LogInstance log1 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 1, 1, 12, 30)).build();
        LogInstance log2 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 1, 2, 22, 20)).build();
        LogInstance log3 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 1, 3, 22, 15)).build();
        LogInstance log4 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 4, 5, 10, 10)).build();
        LogInstance log5 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 6, 7, 6, 10)).build();
        LogInstance log6 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 8, 9, 2, 10)).build();
        LogInstance log7 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2016, 1, 1, 2, 30)).build();
        LogInstance log8 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2020, 12, 12, 23, 30)).build();
        LogInstance log9 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2019, 10, 10, 22, 30)).build();
        LogInstance log10 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2001, 4, 5, 21, 30)).build();

        // act
        for (LogInstance instance : List.of(log1, log2, log3, log4, log5, log6, log7, log8, log9, log10)) {
            counter.accept(instance);
        }
        counter.fill(report);

        // assert
        assertThat(report.mostCommonTimeInterval()).isEqualTo("21:00 -- 23:59");
    }

    @Test
    @DisplayName("standard test 2")
    void standardTest2() {
        // arrange
        MetricCounter counter = new MostCommonTimeIntervalCounter();
        LogReport report = new LogReport();

        LogInstance log1 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 1, 1, 12, 30)).build();
        LogInstance log2 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 1, 2, 12, 29)).build();
        LogInstance log3 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 1, 3, 12, 28)).build();
        LogInstance log4 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 4, 5, 23, 10)).build();
        LogInstance log5 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 6, 7, 23, 10)).build();

        // act
        for (LogInstance instance : List.of(log1, log2, log3, log4, log5)) {
            counter.accept(instance);
        }
        counter.fill(report);

        // assert
        assertThat(report.mostCommonTimeInterval()).isEqualTo("12:00 -- 15:00");
    }

    @Test
    @DisplayName("standard test 3")
    void standardTest3() {
        // arrange
        MetricCounter counter = new MostCommonTimeIntervalCounter();
        LogReport report = new LogReport();

        LogInstance log1 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 1, 1, 1, 30)).build();
        LogInstance log2 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 1, 2, 1, 29)).build();
        LogInstance log3 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 1, 3, 1, 28)).build();
        LogInstance log4 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 4, 5, 23, 10)).build();
        LogInstance log5 = LogInstance.builder().timeLocal(
            LocalDateTime.of(2024, 6, 7, 23, 10)).build();

        // act
        for (LogInstance instance : List.of(log1, log2, log3, log4, log5)) {
            counter.accept(instance);
        }
        counter.fill(report);

        // assert
        assertThat(report.mostCommonTimeInterval()).isEqualTo("00:00 -- 03:00");
    }

    @Test
    @DisplayName("no data test")
    void noDataTest() {
        // arrange
        MetricCounter counter = new MostCommonTimeIntervalCounter();
        LogReport report = new LogReport();

        // act
        counter.fill(report);

        // assert
        assertThat(report.mostCommonTimeInterval()).isNull();
    }
}
