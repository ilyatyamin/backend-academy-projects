package backend.academy.configs;

import com.beust.jcommander.Parameter;
import lombok.Getter;

@Getter
public class LogAnalyzerConfigs {
    @Parameter(
        names = "--path",
        description = "Path to local dirs / file or URL",
        required = true
    )
    private String path;

    @Parameter(
        names = "--from",
        description = "the starting date from which need analyze logs"
    )
    private String fromDate;

    @Parameter(
        names = "--to",
        description = "the end date to which need analyze logs"
    )
    private String toDate;

    @Parameter(
        names = "--format",
        description = "report output format"
    )
    private String format;

    @Parameter(
        names = "--filter-field",
        description = "field that need to be filtered"
    )
    private String filterField;

    @Parameter(
        names = "--filter-value",
        description = "value by what need to filter"
    )
    private String filterValue;

    LogAnalyzerConfigs() {
    }
}
