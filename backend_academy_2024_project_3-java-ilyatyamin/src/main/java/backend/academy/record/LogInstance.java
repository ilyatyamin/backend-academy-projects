package backend.academy.record;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class LogInstance {
    private String remoteAddress;
    private String remoteUser;
    private LocalDateTime timeLocal;
    private String requestMethod;
    private String requestURL;
    private String requestHTTPVersion;
    private String status;
    private Long bodyBytesSent;
    private String httpReferer;
    private String httpUserAgent;

    public boolean isReleasedLaterThan(LocalDate time) {
        return timeLocal.toLocalDate().isAfter(time) || timeLocal.toLocalDate().equals(time);
    }

    public boolean isReleasedEarlierThan(LocalDate time) {
        return timeLocal.toLocalDate().isBefore(time) || timeLocal.toLocalDate().equals(time);
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public String getTimeInterval() {
        LocalTime time = timeLocal.toLocalTime();

        for (int i = 0; i <= 18; i += 3) {
            LocalTime leftBorder = LocalTime.of(i, 0);
            LocalTime rightBorder = LocalTime.of(i + 2, 59);
            if ((time.isAfter(leftBorder) || time.equals(leftBorder))
                && (time.isBefore(rightBorder)) || time.equals(rightBorder)) {
                return String.format("%02d:00 -- %02d:00", i, i + 3);
            }
        }

        LocalTime leftBorder = LocalTime.of(21, 0);
        LocalTime rightBorder = LocalTime.of(23, 59);
        if ((time.isAfter(leftBorder) || time.equals(leftBorder))
            && (time.isBefore(rightBorder)) || time.equals(rightBorder)) {
            return "21:00 -- 23:59";
        }
        return "undefined";
    }
}
