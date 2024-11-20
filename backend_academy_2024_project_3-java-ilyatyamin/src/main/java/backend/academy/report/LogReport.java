package backend.academy.report;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LogReport {
    private long totalRequests;
    private String mostCommonResource;
    private String mostCommonResponseCode;
    private double averageResponseSize;
    private long percentile95OfAnswer;
    private String mostCommonTimeInterval;
    private String mostCommonUserAgent;

    private Map<String, Long> resourcesStats;
    private Map<String, Long> responsesStats;
    private Map<String, Long> timeIntervalStats;
    private Map<String, Long> userAgentStats;
}
