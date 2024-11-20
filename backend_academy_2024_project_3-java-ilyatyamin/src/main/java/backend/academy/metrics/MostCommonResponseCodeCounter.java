package backend.academy.metrics;

import backend.academy.record.LogInstance;
import backend.academy.report.LogReport;
import backend.academy.utils.FrequencyMap;


/**
 * Считает самый частый код ответа HTTP
 */
public class MostCommonResponseCodeCounter implements MetricCounter {
    private final FrequencyMap<String> counter = new FrequencyMap<>();

    @Override
    public void accept(LogInstance logInstance) {
        counter.put(logInstance.status());
    }

    @Override
    public void fill(LogReport report) {
        String mostCommonStatus = counter.getMostCommon();

        report.mostCommonResponseCode(mostCommonStatus);
        report.responsesStats(counter.getSortedMap());
    }
}
