package backend.academy.record;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class LogRecordParserTest {
    @Test
    @DisplayName("standard test")
    void testStandard() {
        // arrange
        String nginxFormat = "54.210.235.191 - - [25/May/2015:13:05:33 +0000] \"GET /downloads/product_2 HTTP/1.1\" 200 951 \"-\" \"urlgrabber/3.9.1 yum/3.4.3\"";

        // act
        LogInstance instance = LogRecordParser.parseNginxString(nginxFormat);

        // assert
        assertThat(instance.remoteAddress()).isEqualTo("54.210.235.191");
        assertThat(instance.remoteUser()).isEqualTo("-");
        assertThat(instance.timeLocal()).isEqualTo(LocalDateTime.of(2015, 5, 25, 13, 5, 33));
        assertThat(instance.requestMethod()).isEqualTo("GET");
        assertThat(instance.requestURL()).isEqualTo("/downloads/product_2");
        assertThat(instance.requestHTTPVersion()).isEqualTo("HTTP/1.1");
        assertThat(instance.status()).isEqualTo("200");
        assertThat(instance.bodyBytesSent()).isEqualTo(951);
        assertThat(instance.httpReferer()).isEqualTo("-");
        assertThat(instance.httpUserAgent()).isEqualTo("urlgrabber/3.9.1 yum/3.4.3");
    }

    @Test
    @DisplayName("incorrect data")
    void incorrectData() {
        // arrange
        String incorrectNginx = "193.30.60.25 # # [25/May/2015:13:05:53 +0000] \"GET /downloads/product_2 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.9.7.9)\"";

        // act
        ThrowableAssert.ThrowingCallable action = () -> LogRecordParser.parseNginxString(incorrectNginx);

        // assert
        assertThatThrownBy(action).isInstanceOf(RuntimeException.class);
    }
}
