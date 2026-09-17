package filtering;

import filtering.process.Process;
import io.github.cdimascio.dotenv.Dotenv;
import model.Log;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Anonymizer implements Process<List<Log>, List<Log>> {

    public static final String ALGORITHM = "HmacSHA256";
    private static final Logger LOG = Logger.getLogger(Anonymizer.class.getName());

    @Override
    public List<Log> process(List<Log> data) {

        for (Log log : data) {
            try {
                String anonymizedIp = anonymizeLogIp(log.getIp());
                log.setIp(anonymizedIp);
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                LOG.log(Level.WARNING,e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return data;
    }


    public String anonymizeLogIp(String ip) throws NoSuchAlgorithmException, InvalidKeyException {

        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        String hmacSecret = System.getenv("SECRET_KEY") != null ? System.getenv("SECRET_KEY") : dotenv.get("SECRET_KEY");

            Mac mac = Mac.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(hmacSecret.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            mac.init(keySpec);

        byte[] hmac = mac.doFinal(ip.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(hmac);
    }

}
