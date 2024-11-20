package backend.academy.configs;

import backend.academy.exceptions.InvalidCommandLineArgsException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class LogAnalyzerConfigsLoaderTest {

    @Test
    @DisplayName("correct test")
    void correctConfigsTest() {
        // arrange
        String[] commandLine = new String[] {
            "--path", "somePath", "--from", "2014-08-31", "--to", "2024-01-01",
            "--format", "adoc", "--filter-field", "status", "--filter-value", "value"};

        // act
        LogAnalyzerConfigs configs = LogAnalyzerConfigsLoader.load(commandLine);

        // assert
        assertThat(configs.path()).isEqualTo("somePath");
        assertThat(configs.format()).isEqualTo("adoc");
        assertThat(configs.filterField()).isEqualTo("status");
        assertThat(configs.filterValue()).isEqualTo("value");
        assertThat(configs.fromDate()).isEqualTo("2014-08-31");
        assertThat(configs.toDate()).isEqualTo("2024-01-01");
    }

    @Test
    @DisplayName("null format")
    void configsWithNullFormatTest() {
        // arrange
        String[] commandLine = new String[] {
            "--path", "somePath", "--from", "2014-08-31", "--to", "2024-01-01",
             "--filter-field", "status", "--filter-value", "value"};

        // act
        LogAnalyzerConfigs configs = LogAnalyzerConfigsLoader.load(commandLine);

        // assert
        assertThat(configs.path()).isEqualTo("somePath");
        assertThat(configs.format()).isEqualTo(null);
        assertThat(configs.filterField()).isEqualTo("status");
        assertThat(configs.filterValue()).isEqualTo("value");
        assertThat(configs.fromDate()).isEqualTo("2014-08-31");
        assertThat(configs.toDate()).isEqualTo("2024-01-01");
    }

    @Test
    @DisplayName("null format")
    void configsWithNullFieldFormatsTest() {
        // arrange
        String[] commandLine = new String[] {
            "--path", "somePath", "--from", "2014-08-31", "--to", "2024-01-01"};

        // act
        LogAnalyzerConfigs configs = LogAnalyzerConfigsLoader.load(commandLine);

        // assert
        assertThat(configs.path()).isEqualTo("somePath");
        assertThat(configs.format()).isEqualTo(null);
        assertThat(configs.filterField()).isEqualTo(null);
        assertThat(configs.filterValue()).isEqualTo(null);
        assertThat(configs.fromDate()).isEqualTo("2014-08-31");
        assertThat(configs.toDate()).isEqualTo("2024-01-01");
    }

    @Test
    @DisplayName("incorrect format")
    void configsWithIncorrectFormatTest() {
        // arrange
        String[] commandLine = new String[] {
            "--path", "somePath", "--from", "2014-08-31", "--to", "2024-01-01", "--format", "strangeFormat"};

        // act
        ThrowableAssert.ThrowingCallable action = () -> LogAnalyzerConfigsLoader.load(commandLine);

        // arrange
        assertThatThrownBy(action).isInstanceOf(InvalidCommandLineArgsException.class);
    }

    @Test
    @DisplayName("incorrect filter field")
    void configsWithIncorrectFilterFieldTest() {
        // arrange
        String[] commandLine = new String[] {
            "--path", "somePath", "--from", "2014-08-31", "--to", "2024-01-01", "--filter-field", "haha"};

        // act
        ThrowableAssert.ThrowingCallable action = () -> LogAnalyzerConfigsLoader.load(commandLine);

        // arrange
        assertThatThrownBy(action).isInstanceOf(InvalidCommandLineArgsException.class);
    }
}
