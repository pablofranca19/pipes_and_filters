package filtering;

import filtering.process.Process;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class Anonymizer implements Process<List<Log>, List<Log>> {

    private List<Log> logList;
    private static final String ALGORITHM = "HmacSHA256";


    public Anonymizer(List<Log> logList) {
        this.logList = logList;
        process(this.logList);
    }

    @Override
    public List<Log> process(List<Log> data) {

        for (Log log : data) {
            try {
                String anonymizedIp = anonymizeLogIp(log.getIp());
                log.setIp(anonymizedIp);
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        return data;
    }


    public String anonymizeLogIp(String ip) throws NoSuchAlgorithmException, InvalidKeyException {

            final String secretKey = "is-it-necessary-any-key";

            // Create a new Mac instance using HMAC-SHA256
            Mac mac = Mac.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            mac.init(keySpec);

            // Generate the HMAC hash
        byte[] hmac = mac.doFinal(ip.getBytes(StandardCharsets.UTF_8));

            // return it already encoded
        return Base64.getEncoder().encodeToString(hmac);
    }

}
