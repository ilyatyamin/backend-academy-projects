package backend.academy.report;

import backend.academy.exceptions.InvalidCommandLineArgsException;
import backend.academy.metrics.AverageResponseSizeCounter;
import backend.academy.metrics.MetricCounter;
import backend.academy.metrics.MostCommonResourceCounter;
import backend.academy.metrics.MostCommonResponseCodeCounter;
import backend.academy.metrics.MostCommonTimeIntervalCounter;
import backend.academy.metrics.MostCommonUserAgentCounter;
import backend.academy.metrics.PercentileResponseSizeCounter;
import backend.academy.metrics.TotalAmountCounter;
import backend.academy.record.LogInstance;
import backend.academy.record.LogRecordParser;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ReportComputer {
    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final List<MetricCounter> counters;

    public ReportComputer() {
        counters = List.of(
            new TotalAmountCounter(),
            new MostCommonResourceCounter(),
            new MostCommonResponseCodeCounter(),
            new AverageResponseSizeCounter(),
            new PercentileResponseSizeCounter(),
            new MostCommonTimeIntervalCounter(),
            new MostCommonUserAgentCounter()
        );
    }

    public LogReport computeMetrics(
        Stream<String> stream,
        String fromDate,
        String toDate,
        String filterField,
        String filterValue
    ) {
        Stream<LogInstance> reports = stream.map(LogRecordParser::parseNginxString);

        if (fromDate != null) {
            LocalDate from = LocalDate.parse(fromDate, DATE_FORMATTER);
            reports = reports.filter(report -> report.isReleasedLaterThan(from));
        }

        if (toDate != null) {
            LocalDate to = LocalDate.parse(toDate, DATE_FORMATTER);
            reports = reports.filter(report -> report.isReleasedEarlierThan(to));
        }

        if (filterField != null && filterValue != null) {
            reports = filterByValue(reports, filterField, filterValue);
        }

        reports.forEach(log -> {
            for (MetricCounter counter : counters) {
                counter.accept(log);
            }
        });

        LogReport report = new LogReport();
        for (MetricCounter counter : counters) {
            counter.fill(report);
        }

        return report;
    }

    private Stream<LogInstance> filterByValue(Stream<LogInstance> reports, String filterField, String filterValue) {
        return switch (filterField) {
            case "remoteAddress" -> reports.filter(report -> Objects.equals(report.remoteAddress(), filterValue));
            case "remoteUser" -> reports.filter(report -> Objects.equals(report.remoteUser(), filterValue));
            case "requestMethod" -> reports.filter(report -> Objects.equals(report.requestMethod(), filterValue));
            case "requestURL" -> reports.filter(report -> Objects.equals(report.requestURL(), filterValue));
            case "requestHTTPVersion" ->
                reports.filter(report -> Objects.equals(report.requestHTTPVersion(), filterValue));
            case "status" -> reports.filter(report -> Objects.equals(String.valueOf(report.status()), filterValue));
            case "bodyBytesSent" ->
                reports.filter(report -> Objects.equals(String.valueOf(report.bodyBytesSent()), filterValue));
            case "httpReferer" -> reports.filter(report -> Objects.equals(report.httpReferer(), filterValue));
            case "httpUserAgent" -> reports.filter(report -> Objects.equals(report.httpUserAgent(), filterValue));
            default -> throw new InvalidCommandLineArgsException("Поле с таким именем не специфицировано.");
        };
    }
}
