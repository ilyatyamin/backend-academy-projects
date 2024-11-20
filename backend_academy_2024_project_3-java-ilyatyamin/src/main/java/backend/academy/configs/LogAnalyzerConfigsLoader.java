package backend.academy.configs;

import backend.academy.exceptions.InvalidCommandLineArgsException;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogAnalyzerConfigsLoader {
    private final static List<String> FIELDS =
        List.of("remoteAddress", "remoteUser", "requestMethod", "requestURL", "requestHTTPVersion", "status",
            "bodyBytesSent", "httpReferer", "httpUserAgent");

    private final static List<String> FORMATS = List.of("markdown", "adoc");

    public static LogAnalyzerConfigs load(String[] args) {
        LogAnalyzerConfigs configs = new LogAnalyzerConfigs();
        JCommander worker = JCommander.newBuilder()
            .addObject(configs)
            .build();

        try {
            worker.parse(args);
        } catch (ParameterException e) {
            throw new InvalidCommandLineArgsException(
                "Проверьте аргументы Вашей командной строки. При парсинге возникла ошибка:\n" + e.getMessage());
        }

        if (configs.filterField() != null && !FIELDS.contains(configs.filterField())) {
            throw new InvalidCommandLineArgsException("Поля, по которому Вы хотите фильтровать, не существует.");
        }
        if (configs.format() != null && !FORMATS.contains(configs.format())) {
            throw new InvalidCommandLineArgsException(
                "Данный формат вывода не поддерживается. Поддерживаемые: markdown, adoc.");
        }

        return configs;
    }
}
