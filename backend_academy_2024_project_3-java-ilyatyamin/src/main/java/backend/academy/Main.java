package backend.academy;

import backend.academy.configs.LogAnalyzerConfigs;
import backend.academy.configs.LogAnalyzerConfigsLoader;
import backend.academy.iohandlers.OutputHandler;
import backend.academy.parsers.FileLogsParser;
import backend.academy.parsers.HttpLogsParser;
import backend.academy.parsers.LogsParser;
import backend.academy.render.AdocRenderer;
import backend.academy.render.MarkdownRenderer;
import backend.academy.render.ReportRenderer;
import backend.academy.report.LogReport;
import backend.academy.report.ReportComputer;
import backend.academy.utils.PathChecker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        OutputHandler output = new OutputHandler(System.out);

        LogAnalyzerConfigs configs = LogAnalyzerConfigsLoader.load(args);
        ReportComputer computer = new ReportComputer();

        LogsParser parser;
        ReportRenderer renderer;

        if (PathChecker.isPathFromNetwork(configs.path())) {
            parser = new HttpLogsParser();
        } else {
            parser = new FileLogsParser();
        }

        var stream = parser.parse(configs.path());

        LogReport report = computer.computeMetrics(stream, configs.fromDate(), configs.toDate(),
            configs.filterField(), configs.filterValue());

        if (configs.format() == null || configs.format().equals("markdown")) {
            renderer = new MarkdownRenderer();
        } else {
            renderer = new AdocRenderer();
        }

        output.print(renderer.render(report));
    }
}
