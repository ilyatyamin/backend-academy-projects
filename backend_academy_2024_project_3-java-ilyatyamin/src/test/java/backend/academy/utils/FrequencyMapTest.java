package backend.academy.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FrequencyMapTest {
    @Test
    @DisplayName("standard text")
    void standardPutTest() {
        // arrange
        FrequencyMap<String> frequencyMap = new FrequencyMap<>();

        Map<String, Long> result = new LinkedHashMap<>();
        result.put("abc", 1L);
        result.put("def", 1L);
        result.put("ghi", 2L);

        // act
        frequencyMap.put("abc");
        frequencyMap.put("def");
        frequencyMap.put("ghi");
        frequencyMap.put("ghi");

        // assert
        assertThat(frequencyMap.getMostCommon()).isEqualTo("ghi");
        assertThat(frequencyMap.getSortedMap()).isEqualTo(result);
    }

    @Test
    @DisplayName("empty map")
    void emptyMapTest() {
        // arrange
        FrequencyMap<Integer> frequencyMap = new FrequencyMap<>();

        // act

        // assert
        assertThat(frequencyMap.getMostCommon()).isNull();
        assertThat(frequencyMap.getSortedMap()).isEqualTo(new LinkedHashMap<>());
    }
}
