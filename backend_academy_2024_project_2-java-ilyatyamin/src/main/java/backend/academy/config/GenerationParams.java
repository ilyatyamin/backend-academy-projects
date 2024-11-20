package backend.academy.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public final class GenerationParams {
    private int generationSizeLeftBorder;
    private int generationSizeRightBorder;

    @JsonCreator
    public GenerationParams(
        @JsonProperty("generationSizeLeftBorder") int generationSizeLeftBorder,
        @JsonProperty("generationSizeRightBorder") int generationSizeRightBorder
    ) {
        if (generationSizeLeftBorder < 0 || generationSizeRightBorder < 0) {
            throw new RuntimeException("Invalid generation parameters");
        }
        this.generationSizeLeftBorder = generationSizeLeftBorder;
        this.generationSizeRightBorder = generationSizeRightBorder;
    }
}
