package backend.academy.metrics;

import backend.academy.record.LogInstance;
import backend.academy.report.LogReport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MostCommonResourceTest {
    @Test
    @DisplayName("standard test")
    void standardTest() {
        // arrange
        MostCommonResourceCounter mostCommonResourceCounter = new MostCommonResourceCounter();
        LogReport logReport = new LogReport();

        LogInstance log1 = LogInstance.builder().requestURL("resource1").build();
        LogInstance log2 = LogInstance.builder().requestURL("resource1").build();
        LogInstance log3 = LogInstance.builder().requestURL("resource2").build();
        LogInstance log4 = LogInstance.builder().requestURL("resource2").build();
        LogInstance log5 = LogInstance.builder().requestURL("resource2").build();
        LogInstance log6 = LogInstance.builder().requestURL("resource5").build();


        // act
        mostCommonResourceCounter.accept(log1);
        mostCommonResourceCounter.accept(log2);
        mostCommonResourceCounter.accept(log3);
        mostCommonResourceCounter.accept(log4);
        mostCommonResourceCounter.accept(log5);
        mostCommonResourceCounter.accept(log6);

        mostCommonResourceCounter.fill(logReport);

        // assert
        assertThat(logReport.mostCommonResource()).isEqualTo("resource2");
    }

    @Test
    @DisplayName("no data test")
    void noDataTest() {
        // arrange
        MetricCounter mostCommonResourceCounter = new MostCommonResourceCounter();
        LogReport logReport = new LogReport();

        // act
        mostCommonResourceCounter.fill(logReport);

        // assert
        assertThat(logReport.mostCommonResource()).isNull();
    }
}
