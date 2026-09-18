package input_test;

import static filtering.Validator.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

class ValidationTest {


    @Test
    @DisplayName("Verify if the log matches the regex")
    void ifRegexMatchText () {
        final Pattern regexPattern = Pattern.compile("^" + IP_REGEX + "\\s-\\s-\\s" + "\\[" + DATE_REGEX + "\\]" + "\\s" + "\"" +  HTTP_REQUEST_REGEX + "\\s" + ENDPOINT_REGEX + "\\s" + HTTP_VERSION_REGEX + "\"" + "\\s" + STATUS_CODE_REGEX + "\\s" + BYTE_LENGTH_REGEX + "$");
        String log2 = "172.16.0.4 - - [10/Mar/2026:19:09:01] \"GET /admin HTTP/1.1\" 201 0";

        assertTrue(log2.matches(regexPattern.pattern()), log2);
    }

    @Test
    @DisplayName("Testing BufferedReader")
    void checkIfBufferedReaderIsActuallyReading () {
        String path = "src/logs/logs.txt";
        File file = new File(path);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            assertThat(bufferedReader).isNotNull();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("There's no content in your file path: " + e);
        }

    }

    @Test
    @DisplayName("Checking regex logic with conditional")
    void mustAddToAListOfStringIfMatches() {

        List<String> validatedLogs = new ArrayList<>();

        Path filePath = Path.of("src/logs/logs.txt");

        try (BufferedReader bufferedReader = new BufferedReader(Files.newBufferedReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.matches(LOG_REGEX.pattern())) {
                    validatedLogs.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertThat(validatedLogs).isNotEmpty();

    }
}
