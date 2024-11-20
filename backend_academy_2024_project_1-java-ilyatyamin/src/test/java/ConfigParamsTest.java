import backend.academy.configs.ConfigParams;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class ConfigParamsTest {
    @Test
    void testStandardValue() {
        // arrange
        ConfigParams params = new ConfigParams(3);

        // act
        int numberOfAttempts = params.numberAttempts();

        // assert
        assertThat(numberOfAttempts).isEqualTo(3);
    }

    @Test
    void testExtremelyBigValue() {
        // arrange
        ConfigParams params = new ConfigParams((int)1e5);

        // act
        int numberOfAttempts = params.numberAttempts();

        // assert
        assertThat(numberOfAttempts).isEqualTo(5);
    }

    @Test
    void testNegativeValue() {
        // arrange
        ConfigParams params = new ConfigParams(-10);

        // act
        int numberOfAttempts = params.numberAttempts();

        // assert
        assertThat(numberOfAttempts).isEqualTo(5);
    }
}
