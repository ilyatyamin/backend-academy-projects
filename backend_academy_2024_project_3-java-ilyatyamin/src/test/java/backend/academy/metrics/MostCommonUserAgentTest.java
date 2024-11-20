package backend.academy.metrics;

import backend.academy.record.LogInstance;
import backend.academy.report.LogReport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MostCommonUserAgentTest {
    @Test
    @DisplayName("standard test")
    void standardTest() {
        // arrange
        MetricCounter counter = new MostCommonUserAgentCounter();
        LogReport report = new LogReport();

        LogInstance log1 = LogInstance.builder().httpUserAgent("ubuntu 1").build();
        LogInstance log2 = LogInstance.builder().httpUserAgent("ubuntu 2").build();
        LogInstance log3 = LogInstance.builder().httpUserAgent("vk bot").build();
        LogInstance log4 = LogInstance.builder().httpUserAgent("vk bot").build();
        LogInstance log5 = LogInstance.builder().httpUserAgent("tg bot").build();

        // act
        counter.accept(log1);
        counter.accept(log2);
        counter.accept(log3);
        counter.accept(log4);
        counter.accept(log5);
        counter.fill(report);

        // assert
        assertThat(report.mostCommonUserAgent()).isEqualTo("vk bot");
    }

    @Test
    @DisplayName("no data test")
    void noDataTest() {
        // arrange
        MetricCounter counter = new MostCommonUserAgentCounter();
        LogReport report = new LogReport();

        // act
        counter.fill(report);

        // assert
        assertThat(report.mostCommonUserAgent()).isEqualTo(null);
    }
}
