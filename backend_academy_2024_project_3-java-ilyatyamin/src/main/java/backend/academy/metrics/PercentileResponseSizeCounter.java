package backend.academy.metrics;

import backend.academy.record.LogInstance;
import backend.academy.report.LogReport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Algorithm is: sum all values from your map, calculate 95% of the sum, iterate the map keys
 * in ascending order keeping a running total of values, and when sum equals
 * or exceeds the previously calculated 95% of the total sum, the key should be the 95th percentile.
 * <a href="https://stackoverflow.com/questions/15714412/how-to-calculate-95-percentile-in-java-using-the-datasets-in-map">...</a>
 */
public class PercentileResponseSizeCounter implements MetricCounter {
    private final double percentile;

    private final List<Long> values = new ArrayList<>();
    private long sumOfValues = 0;

    @SuppressWarnings("checkstyle:MagicNumber")
    public PercentileResponseSizeCounter() {
        this.percentile = 0.95;
    }

    public PercentileResponseSizeCounter(double percentile) {
        this.percentile = percentile;
    }

    @Override
    public void accept(LogInstance logInstance) {
        sumOfValues += logInstance.bodyBytesSent();
        values.add(logInstance.bodyBytesSent());
    }

    @Override
    public void fill(LogReport report) {
        Collections.sort(values);

        double goal = sumOfValues * percentile;

        long localSum = 0;
        for (Long value : values) {
            localSum += value;
            if (localSum >= goal) {
                report.percentile95OfAnswer(value);
            }
        }
    }
}
