package backend.academy.metrics;

import backend.academy.record.LogInstance;
import backend.academy.report.LogReport;
import backend.academy.utils.FrequencyMap;

/**
 * Считает самый частый user-agent во всех логах
 */
public class MostCommonUserAgentCounter implements MetricCounter {
    private final FrequencyMap<String> counter = new FrequencyMap<>();

    @Override
    public void accept(LogInstance logInstance) {
        counter.put(logInstance.httpUserAgent());
    }

    @Override
    public void fill(LogReport report) {
        report.mostCommonUserAgent(counter.getMostCommon());
        report.userAgentStats(counter.getSortedMap());
    }
}
