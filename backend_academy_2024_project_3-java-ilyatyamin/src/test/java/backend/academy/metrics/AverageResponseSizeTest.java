package backend.academy.metrics;

import backend.academy.record.LogInstance;
import backend.academy.report.LogReport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AverageResponseSizeTest {
    @Test
    @DisplayName("default check")
    void defaultCheck() {
        // arrange
        AverageResponseSizeCounter metric = new AverageResponseSizeCounter();

        LogInstance log1 = LogInstance.builder().bodyBytesSent(10L).build();
        LogInstance log2 = LogInstance.builder().bodyBytesSent(20L).build();
        LogInstance log3 = LogInstance.builder().bodyBytesSent(60L).build();

        LogReport report = new LogReport();

        // act
        metric.accept(log1);
        metric.accept(log2);
        metric.accept(log3);

        metric.fill(report);

        // assert
        assertThat(report.averageResponseSize()).isEqualTo(30L);
    }

    @Test
    @DisplayName("empty metric")
    void emptyMetricTest() {
        // arrange
        AverageResponseSizeCounter metric = new AverageResponseSizeCounter();
        LogReport report = new LogReport();

        // act
        metric.fill(report);

        // assert
        assertThat(report.averageResponseSize()).isEqualTo(0L);
    }
}
