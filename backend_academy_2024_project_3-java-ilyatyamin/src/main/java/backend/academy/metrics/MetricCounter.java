package backend.academy.metrics;

import backend.academy.record.LogInstance;
import backend.academy.report.LogReport;
import java.util.function.Consumer;

public interface MetricCounter extends Consumer<LogInstance> {
    void fill(LogReport report);
}
