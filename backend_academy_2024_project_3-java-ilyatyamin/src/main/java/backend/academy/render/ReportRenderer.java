package backend.academy.render;

import backend.academy.report.LogReport;
import java.util.HashMap;
import java.util.Map;

public abstract class ReportRenderer {
    public abstract String render(LogReport report);

    protected Map<String, String> getTotalStatsMap(LogReport report) {
        Map<String, String> valuesToBePrinted = new HashMap<>();
        valuesToBePrinted.put("Всего запросов", String.valueOf(report.totalRequests()));
        valuesToBePrinted.put("Самый частый ресурс", report.mostCommonResource());
        valuesToBePrinted.put("Самый частый статус HTTP", report.mostCommonResponseCode());
        valuesToBePrinted.put("Средний размер ответа", String.format("%.2f", report.averageResponseSize()));
        valuesToBePrinted.put("Перцентиль 95% размеров ответа", String.valueOf(report.percentile95OfAnswer()));
        valuesToBePrinted.put("Самый частый временной интервал посылки запросов", report.mostCommonTimeInterval());
        valuesToBePrinted.put("Самый частый user-agent", report.mostCommonUserAgent());
        return valuesToBePrinted;
    }

    protected <K> void fillTable(
        StringBuilder table, int maxSizeKeys,
        Map<String, K> values
    ) {
        String firstColumnName = "Метрики";
        String secondColumnName = "Значения";

        String header = formatRow(firstColumnName, secondColumnName, maxSizeKeys);
        String divider = formatRow("-".repeat(maxSizeKeys), "-".repeat(maxSizeKeys), maxSizeKeys);

        table.append(header)
            .append(divider);

        for (var entry : values.entrySet()) {
            if (entry.getValue() != null) {
                table.append(formatRow(entry.getKey(), entry.getValue().toString(), maxSizeKeys));
            }
        }
    }

    protected String formatRow(String left, String right, int maxSize) {
        return String.format("| %s | %s |\n",
            left + " ".repeat(Math.max(0, maxSize - left.length())),
            right + " ".repeat(Math.max(0, maxSize - right.length())));
    }

    protected <K> int getMaxSize(Map<String, K> values) {
        int maxSizeKeys = 0;
        for (String key : values.keySet()) {
            if (key.length() > maxSizeKeys) {
                maxSizeKeys = key.length();
            }
        }
        return maxSizeKeys;
    }
}
