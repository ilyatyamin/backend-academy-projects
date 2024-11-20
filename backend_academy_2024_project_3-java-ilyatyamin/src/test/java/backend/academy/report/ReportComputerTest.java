package backend.academy.report;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReportComputerTest {
    @Test
    @DisplayName("default test")
    void defaultTest1() {
        // arrange
        ReportComputer computer = new ReportComputer();

        String logs = """
            50.57.209.100 - - [25/May/2015:05:05:37 +0000] "GET /downloads/product_1 HTTP/1.1" 404 334 "-" "Debian APT-HTTP/1.3 (0.9.7.9)"
            94.236.106.132 - - [25/May/2015:05:05:59 +0000] "GET /downloads/product_1 HTTP/1.1" 404 336 "-" "Debian APT-HTTP/1.3 (0.9.7.9)"
            119.252.76.162 - - [25/May/2015:05:05:33 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (1.0.1ubuntu2)"
            """;
        Stream<String> instances = Arrays.stream(logs.split("\n"));
        String from = "2015-05-24";
        String to = "2015-05-26";

        // act
        LogReport report = computer.computeMetrics(instances, from, to, "remoteAddress", "50.57.209.100");

        // assert
        assertThat(report.totalRequests()).isEqualTo(1);
        assertThat(report.mostCommonUserAgent()).isEqualTo("Debian APT-HTTP/1.3 (0.9.7.9)");
        assertThat(report.percentile95OfAnswer()).isEqualTo(334);
        assertThat(report.mostCommonResponseCode()).isEqualTo("404");
        assertThat(report.mostCommonTimeInterval()).isEqualTo("03:00 -- 06:00");
        assertThat(report.mostCommonResource()).isEqualTo("/downloads/product_1");
    }

    @Test
    @DisplayName("default test - 2")
    void defaultTest2() {
        // arrange
        ReportComputer computer = new ReportComputer();

        String logs = """
            50.57.209.100 - - [25/May/2015:05:05:37 +0000] "GET /downloads/product_1 HTTP/1.1" 404 334 "-" "Debian APT-HTTP/1.3 (0.9.7.9)"
            94.236.106.132 - - [25/May/2015:05:05:59 +0000] "GET /downloads/product_1 HTTP/1.1" 404 336 "-" "Debian APT-HTTP/1.3 (0.9.7.9)"
            119.252.76.162 - - [25/May/2015:05:05:33 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (1.0.1ubuntu2)"
            """;
        Stream<String> instances = Arrays.stream(logs.split("\n"));
        String from = "2015-05-24";
        String to = "2015-05-26";

        // act
        LogReport report = computer.computeMetrics(instances, from, to, "remoteAddress", null);

        // assert
        assertThat(report.totalRequests()).isEqualTo(3);
        assertThat(report.mostCommonUserAgent()).isEqualTo("Debian APT-HTTP/1.3 (0.9.7.9)");
        assertThat(report.percentile95OfAnswer()).isEqualTo(336);
        assertThat(report.mostCommonResponseCode()).isEqualTo("404");
        assertThat(report.mostCommonTimeInterval()).isEqualTo("03:00 -- 06:00");
        assertThat(report.mostCommonResource()).isEqualTo("/downloads/product_1");
    }

    @Test
    @DisplayName("default test - 3")
    void defaultTest3() {
        // arrange
        ReportComputer computer = new ReportComputer();
        LogReport report2 = LogReport.builder().build();

        String logs = """
            50.57.209.100 - - [25/May/2015:05:05:37 +0000] "GET /downloads/product_1 HTTP/1.1" 404 334 "-" "Debian APT-HTTP/1.3 (0.9.7.9)"
            94.236.106.132 - - [28/May/2015:05:05:59 +0000] "GET /downloads/product_1 HTTP/1.1" 404 336 "-" "Debian APT-HTTP/1.3 (0.9.7.9)"
            119.252.76.162 - - [30/May/2015:05:05:33 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (1.0.1ubuntu2)"
            """;
        Stream<String> instances = Arrays.stream(logs.split("\n"));
        String from = "2015-05-26";
        String to = "2015-05-29";

        // act
        LogReport report = computer.computeMetrics(instances, from, to, "remoteAddress", null);

        // assert
        assertThat(report.totalRequests()).isEqualTo(1);
        assertThat(report.mostCommonUserAgent()).isEqualTo("Debian APT-HTTP/1.3 (0.9.7.9)");
        assertThat(report.percentile95OfAnswer()).isEqualTo(336);
        assertThat(report.mostCommonResponseCode()).isEqualTo("404");
        assertThat(report.mostCommonTimeInterval()).isEqualTo("03:00 -- 06:00");
        assertThat(report.mostCommonResource()).isEqualTo("/downloads/product_1");
    }
}
