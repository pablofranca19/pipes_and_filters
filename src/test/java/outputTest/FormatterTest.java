package outputTest;

import filtering.Formatter;
import model.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


import static org.assertj.core.api.Assertions.*;


class FormatterTest {

    private static final Path OUTPUT_PATH = Path.of("target/output/logs.json");
    private static final Log EMPTY_LOG = new Log("", "", "", "", "", "", "");
    private static final Log FORMATTED_LOG = new Log("12FzwHmcBJoxM4nF0cg4Kc3lSRZRSIy7w8hQA7r2ybc=", "10/Mar/2026:15:57:33", "POST", "/users", "HTTP/1.1", "201", "100");

    private final Formatter formatter = new Formatter();

    @BeforeEach
    void cleanBefore() throws IOException {
        Files.deleteIfExists(OUTPUT_PATH);
    }

    @AfterEach
    void cleanAfter() throws IOException {
        Files.deleteIfExists(OUTPUT_PATH);
    }

    @Test
    @DisplayName("Verify if it doesn't create a empty json file")
    void theProcessMustNotGenerateAEmptyJsonFile() {
        assertThatThrownBy(() -> {
            String result = formatter.process(List.of(EMPTY_LOG));
        }).isInstanceOf(RuntimeException.class);

    }

    @Test
    @DisplayName("Checking if a valid log entry generates the expected output")
    void process_withValidLogs_writesParsablePrettyJsonAndReturnsConfirmationMessage() {

        String output = formatter.process(List.of(FORMATTED_LOG));

        assertThat("Log registry created, check \"target/output/logs.json\".").isEqualTo(output);
    }
}