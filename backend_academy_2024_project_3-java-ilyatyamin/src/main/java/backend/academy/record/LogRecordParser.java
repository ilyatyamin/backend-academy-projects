package backend.academy.record;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogRecordParser {
    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", java.util.Locale.ENGLISH);

    @SuppressWarnings("checkstyle:LineLength")
    private static final String NGINX_REGULAR =
        "(\\S+) - (\\S+) \\[([\\w:/]+\\s[+\\\\-]\\d{4})] \"(\\S+)\\s?(\\S+)\\s?(\\S+)?\" (\\d{3}|-) (\\d+|-)\\s?\"?([^\"]*)\"?\\s?\"?([^\"]*)?\"?";

    @SuppressWarnings("checkstyle:MagicNumber")
    public static LogInstance parseNginxString(String line) {
        Pattern pattern = Pattern.compile(NGINX_REGULAR);
        Matcher matcher = pattern.matcher(line);

        var builder = LogInstance.builder();

        if (matcher.find()) {
            String localTimeStr = matcher.group(3);
            LocalDateTime localTime = LocalDateTime.parse(localTimeStr, FORMATTER);
            Long bodyBytesSent = Long.parseLong(matcher.group(8));

            builder = builder.remoteAddress(matcher.group(1))
                .remoteUser(matcher.group(2))
                .timeLocal(localTime)
                .requestMethod(matcher.group(4))
                .requestURL(matcher.group(5))
                .requestHTTPVersion(matcher.group(6))
                .status(matcher.group(7))
                .bodyBytesSent(bodyBytesSent)
                .httpReferer(matcher.group(9))
                .httpUserAgent(matcher.group(10));

            return builder.build();
        } else {
            throw new RuntimeException("incorrect data");
        }
    }
}
