package backend.academy.metrics;

import backend.academy.record.LogInstance;
import backend.academy.report.LogReport;

/**
 * Считает сколько всего было запросов
 */
public class TotalAmountCounter implements MetricCounter {
    private long counter = 0;

    @Override
    public void accept(LogInstance logRecord) {
        counter += 1;
    }

    @Override
    public void fill(LogReport report) {
        report.totalRequests(counter);
    }
}
