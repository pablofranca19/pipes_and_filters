package processTest;

import model.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;

import static filtering.Anonymizer.ALGORITHM;

public class AnonymizerTest {

    @Test
    @DisplayName("Verify if the log ip is being tokenized")
    void testAnonymizeIp() throws NoSuchAlgorithmException, InvalidKeyException {

        Log testLog = new Log("168.0.0.1", "10/Mar/2026:19:09:01", "PATCH", "/api", "HTTP/1.2", "500", "200");

        Mac mac = Mac.getInstance(ALGORITHM);
        final String secretKey = "test-key";
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);

        mac.init(keySpec);

        byte[] hmac = mac.doFinal(testLog.getIp().getBytes(StandardCharsets.UTF_8));
        String token = Base64.getEncoder().encodeToString(hmac);

        testLog.setIp(token);

        assertThat(testLog.getIp()).isEqualTo("12FzwHmcBJoxM4nF0cg4Kc3lSRZRSIy7w8hQA7r2ybc=");


    }

}
