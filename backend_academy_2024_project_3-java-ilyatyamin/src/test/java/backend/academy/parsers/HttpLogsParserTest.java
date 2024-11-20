package backend.academy.parsers;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class HttpLogsParserTest {
    @Test
    @DisplayName("default test")
    void defaultTest() {
        // arrange
        String url = "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";
        String mockedData = "\"54.210.235.191 - - [25/May/2015:13:05:33 +0000] \\\"GET /downloads/product_2 HTTP/1.1\\\" 200 951 \\\"-\\\" \\\"urlgrabber/3.9.1 yum/3.4.3\\\"";

        LogsParser parser = Mockito.mock(HttpLogsParser.class);
        Mockito.when(parser.parse(url)).thenReturn(Stream.of(mockedData));

        // act
        var data = parser.parse(url);

        // assert
        assertThat(data.count()).isPositive();
    }

    @Test
    @DisplayName("incorrect url")
    void incorrectUrl() {
        // arrange
        String url = "https://heheheheheh3h2jrj3nd3nnd3m3nmd.ru";
        LogsParser parser = new HttpLogsParser();

        // act
        ThrowableAssert.ThrowingCallable action = () -> parser.parse(url);

        // assert
        assertThatThrownBy(action).isInstanceOf(RuntimeException.class);
    }
}
