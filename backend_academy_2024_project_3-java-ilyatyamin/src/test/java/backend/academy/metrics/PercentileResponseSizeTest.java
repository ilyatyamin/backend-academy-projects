package backend.academy.metrics;

import backend.academy.record.LogInstance;
import backend.academy.report.LogReport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PercentileResponseSizeTest {
    @Test
    @DisplayName("standard test")
    void standardTest() {
        // arrange
        MetricCounter counter = new PercentileResponseSizeCounter();
        LogReport report = new LogReport();

        LogInstance log1 = LogInstance.builder().bodyBytesSent(0L).build();
        LogInstance log2 = LogInstance.builder().bodyBytesSent(3L).build();
        LogInstance log3 = LogInstance.builder().bodyBytesSent(2L).build();
        LogInstance log4 = LogInstance.builder().bodyBytesSent(2L).build();
        LogInstance log5 = LogInstance.builder().bodyBytesSent(5L).build();
        // sum = 11. 11 * 0.95 = 10.45

        // act
        counter.accept(log1);
        counter.accept(log2);
        counter.accept(log3);
        counter.accept(log4);
        counter.accept(log5);
        counter.fill(report);

        // assert
        assertThat(report.percentile95OfAnswer()).isEqualTo(5L);
    }

    @Test
    @DisplayName("no data test")
    void noDataTest() {
        // arrange
        MetricCounter counter = new PercentileResponseSizeCounter();
        LogReport report = new LogReport();

        // act
        counter.fill(report);

        // assert
        assertThat(report.percentile95OfAnswer()).isEqualTo(0L);
    }
}
