package backend.academy.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class GenerationParamsLoaderTest {
    @Test
    @DisplayName("Test for correct json parsing")
    void testMockParams() {
        // arrange
        GenerationParamsLoader loader = Mockito.spy(GenerationParamsLoader.class);
        String jsonParams = """
            {
                "generationSizeLeftBorder" : 3,
                "generationSizeRightBorder" : 100
            }
            """;

        // act
        Mockito.when(loader.getContext())
            .thenReturn(new ByteArrayInputStream(jsonParams.getBytes(StandardCharsets.UTF_8)));
        GenerationParams params = loader.loadFromConfigFile();

        // assert
        assertThat(params.generationSizeLeftBorder()).isEqualTo(3);
        assertThat(params.generationSizeRightBorder()).isEqualTo(100);
    }

    @Test
    @DisplayName("Test for incorrect values in json")
    void testIncorrectMockParamws() {
        // arrange
        GenerationParamsLoader loader = Mockito.spy(GenerationParamsLoader.class);
        String jsonParams = """
            {
                "generationSizeLeftBorder" : -10,
                "generationSizeRightBorder" : 100
            }
            """;

        // act
        Mockito.when(loader.getContext())
            .thenReturn(new ByteArrayInputStream(jsonParams.getBytes(StandardCharsets.UTF_8)));

        // assert
        assertThatThrownBy(loader::loadFromConfigFile).isInstanceOf(RuntimeException.class);
    }
}
