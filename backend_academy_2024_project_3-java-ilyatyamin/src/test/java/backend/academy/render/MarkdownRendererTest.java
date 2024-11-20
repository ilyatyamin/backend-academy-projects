package backend.academy.render;

import backend.academy.report.LogReport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MarkdownRendererTest {
    @Test
    @DisplayName("standard test")
    void standardTest() {
        // arrange
        LogReport report = new LogReport();
        ReportRenderer renderer = new MarkdownRenderer();

        // act
        String render = renderer.render(report);

        // assert
        assertThat(render.length()).isPositive();
        assertThat(render.contains("Общая статистика")).isTrue();
        assertThat(render.contains("##")).isTrue();
        assertThat(render.contains("===")).isFalse();
    }
}
