import backend.academy.configs.ConfigLoader;
import backend.academy.configs.ConfigParams;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ConfigLoaderTest {
    @Test
    void testLoadingConfig() {
        // arrange
        InputStream is = new ByteArrayInputStream("{\"numberAttempts\" : 5}".getBytes());

        // act
        ConfigParams params = ConfigLoader.loadFromStream(is);

        // assert
        assertThat(params.numberAttempts()).isEqualTo(5);
    }

    @Test
    void testLoadingConfigWithInvalidNumber() {
        // arrange
        InputStream is = new ByteArrayInputStream("{\"numberAttempts\" : 10000}".getBytes());

        // act
        ConfigParams params = ConfigLoader.loadFromStream(is);

        // assert
        assertThat(params.numberAttempts()).isEqualTo(5);
    }

    @Test
    void testLoadingConfigWithInvalidStream() {
        // arrange
        InputStream is = new ByteArrayInputStream("".getBytes());

        // act
        ThrowableAssert.ThrowingCallable action = (() -> ConfigLoader.loadFromStream(is));

        // assert
        assertThatThrownBy(action).isInstanceOf(RuntimeException.class);
    }
}
