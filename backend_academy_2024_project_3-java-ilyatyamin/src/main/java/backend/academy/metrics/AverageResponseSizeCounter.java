package backend.academy.metrics;

import backend.academy.record.LogInstance;
import backend.academy.report.LogReport;

/**
 * Считает средний размер отправленных данных
 */
public class AverageResponseSizeCounter implements MetricCounter {
    private long sumOfResponseSize = 0;
    private long countOfResponses = 0;

    @Override
    public void accept(LogInstance logInstance) {
        countOfResponses += 1;
        sumOfResponseSize += logInstance.bodyBytesSent();
    }

    @Override
    public void fill(LogReport report) {
        if (countOfResponses == 0) {
            report.averageResponseSize(0);
        } else {
            double average = (double) sumOfResponseSize / countOfResponses;
            report.averageResponseSize(average);
        }
    }
}
