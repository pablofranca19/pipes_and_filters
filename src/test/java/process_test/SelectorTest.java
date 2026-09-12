package process_test;


import model.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SelectorTest {

    @Test
    @DisplayName("Verify if regex formats to create Log object fields")
    void matchesObject () {
        String line = "172.16.0.4 - - [10/Mar/2026:19:09:01] \"GET /admin HTTP/1.1\" 201 0";
        String result = line.replaceAll("[\\-\"\\[\\]]", "");
        String[] logFields = result.split("\\s+");
        Log log = new Log(logFields[0], logFields[1], logFields[2], logFields[3], logFields[4], logFields[5], logFields[6]);

        assertThat(logFields[0]).matches(log.getIp());
        assertThat(logFields[1]).matches(log.getDate());
        assertThat(logFields[2]).matches(log.getRequestType());
        assertThat(logFields[3]).matches(log.getEndpoint());
        assertThat(logFields[4]).matches(log.getHttpVersion());
        assertThat(logFields[5]).matches(log.getStatusCode());
        assertThat(logFields[6]).matches(log.getByteLength());
    }

}
