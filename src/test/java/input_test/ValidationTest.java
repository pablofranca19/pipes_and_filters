package input_test;

import static filtering.Validator.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

class ValidationTest {


    @Test
    @DisplayName("Verify if the log matches the regex")
    void ifRegexMatchText () {
        final String LOG_REGEX = "^" + IP_REGEX + "\\s-\\s-\\s" + "\\[" + DATE_REGEX + "\\]" + "\\s" + "\"" +  HTTP_REQUEST_REGEX + "\\s" + ENDPOINT_REGEX + "\\s" + HTTP_VERSION_REGEX + "\"" + "\\s" + STATUS_CODE_REGEX + "\\s" + BYTE_LENGTH_REGEX + "$";
        String log2 = "172.16.0.4 - - [10/Mar/2026:19:09:01] \"GET /admin HTTP/1.1\" 201 0";

        assertTrue(log2.matches(LOG_REGEX), log2);
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


}
