package backend.academy.render;

import backend.academy.report.LogReport;
import java.util.Map;

public class AdocRenderer extends ReportRenderer {
    private static final String MARK_TABLE = "|===\n";

    @Override
    public String render(LogReport report) {
        StringBuilder result = new StringBuilder();

        result.append("=== Общая статистика\n");

        Map<String, String> valuesToBePrinted = getTotalStatsMap(report);
        result.append(makeTable(valuesToBePrinted));

        result.append("\n=== Статистика по ресурсам\n");
        result.append(makeTable(report.resourcesStats()));

        result.append("\n=== Статистика по статусам HTTP\n");
        result.append(makeTable(report.responsesStats()));

        result.append("\n=== Статистика по временным интервалам\n");
        result.append(makeTable(report.timeIntervalStats()));

        result.append("\n=== Статистика по user-agent\n");
        result.append(makeTable(report.userAgentStats()));

        return result.toString();
    }

    private <K> String makeTable(Map<String, K> values) {
        if (values == null) {
            return "";
        }

        // detect max size of keys
        int maxSizeKeys = getMaxSize(values);

        StringBuilder table = new StringBuilder();
        table.append("[cols=2*,options=\"header\",cols=\"25,75\"]\n");
        table.append(MARK_TABLE);

        fillTable(table, maxSizeKeys, values);

        table.append(MARK_TABLE);
        return table.toString();
    }
}
