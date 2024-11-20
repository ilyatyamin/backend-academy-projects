package backend.academy.configs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ConfigParams {
    @SuppressWarnings("checkstyle:MagicNumber")
    @JsonCreator
    public ConfigParams(
        @JsonProperty("numberAttempts") int numberAttempts
    ) {
        if (numberAttempts >= 9 || numberAttempts <= 0) {
            this.numberAttempts = 5;
        } else {
            this.numberAttempts = numberAttempts;
        }
    }

    private final int numberAttempts;
}
