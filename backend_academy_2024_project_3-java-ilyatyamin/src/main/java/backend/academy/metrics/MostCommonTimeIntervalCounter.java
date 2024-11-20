package backend.academy.metrics;

import backend.academy.record.LogInstance;
import backend.academy.report.LogReport;
import backend.academy.utils.FrequencyMap;

// В этом метрике я разделяю время запросов на 8 промежутков: 0-3 часа, 3-6, 6-9, ... 21-24 часа
// И выясняю в какое время делается больше всего запросов
public class MostCommonTimeIntervalCounter implements MetricCounter {
    private final FrequencyMap<String> counter = new FrequencyMap<>();

    @Override
    public void accept(LogInstance logInstance) {
        String timeInterval = logInstance.getTimeInterval();
        counter.put(timeInterval);
    }

    @Override
    public void fill(LogReport report) {
        String mostCommonTimeInterval = counter.getMostCommon();

        report.mostCommonTimeInterval(mostCommonTimeInterval);
        report.timeIntervalStats(counter.getSortedMap());
    }
}
