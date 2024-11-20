package backend.academy.metrics;

import backend.academy.record.LogInstance;
import backend.academy.report.LogReport;
import backend.academy.utils.FrequencyMap;

/**
 * Считает самый ресурс, на который было отправлено больше всего запросов
 */
public class MostCommonResourceCounter implements MetricCounter {
    private final FrequencyMap<String> counter = new FrequencyMap<>();

    @Override
    public void accept(LogInstance logRecord) {
        counter.put(logRecord.requestURL());
    }

    @Override
    public void fill(LogReport report) {
        String mostCommon = counter.getMostCommon();

        report.mostCommonResource(mostCommon);
        report.resourcesStats(counter.getSortedMap());
    }
}
