package filtering;

import filtering.process.Process;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;



public class Validator implements Process<String, List<String>>  {

    private static final String IP_REGEX = "((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}";
    private static final String DATE_REGEX = "(\\d{2})/(\\w{3})/(\\d{4}):(\\d{2}):(\\d{2}):(\\d{2})";
    private static final String HTTP_REQUEST_REGEX = "[a-zA-Z]{3,6}\\p{javaUpperCase}";
    private static final String ENDPOINT_REGEX = "/\\w+";
    private static final String HTTP_VERSION_REGEX = "HTTP/\\d\\.\\d";
    private static final String STATUS_CODE_REGEX = "\\d{3}";
    private static final String BYTE_LENGTH_REGEX = "\\d+";
    // StringBuilder for this
    private static final String LOG_REGEX = "^" + IP_REGEX + "\\s-\\s-\\s" + "\\[" + DATE_REGEX + "\\]" + "\\s" + "\"" +  HTTP_REQUEST_REGEX + "\\s" + ENDPOINT_REGEX + "\\s" + HTTP_VERSION_REGEX + "\"" + "\\s" + STATUS_CODE_REGEX + "\\s" + BYTE_LENGTH_REGEX + "$";

    @Override
    public List<String> process(String path) {
        List<String> validatedLogs = new ArrayList<>();
        Path filePath = Path.of(path);

        try (BufferedReader bufferedReader = new BufferedReader(Files.newBufferedReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.matches(LOG_REGEX)) {
                    validatedLogs.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        return validatedLogs;
    }

    // only meant for testing (yes, outside of junit, just for a while)
    /*
    static void main() {
        String log = "192.168.1.1 - - [10/Mar/2026:02:18:02] \"POST /login HTTP/1.1\" 301 0";
        String ip = "192.168.1.1";
        String date = "10/Mar/2026:02:18:02";
        String httpMethod = "DELETE";
        String endpoint = "/login";
        String version = "HTTP/1.1";
        String statusCode = "301";
        String byteLen = "0";

        System.out.println("Line test: " + log.matches(LOG_REGEX));
        System.out.println("Ip test: " + ip.matches(IP_REGEX));
        System.out.println("Date test: " + date.matches(DATE_REGEX));
        System.out.println("HTTP Method test: " + httpMethod.matches(HTTP_REQUEST_REGEX));
        System.out.println("Endpoint test: " + endpoint.matches(ENDPOINT_REGEX));
        System.out.println("Version test: " + version.matches(HTTP_VERSION_REGEX));
        System.out.println("Status code test: " + statusCode.matches(STATUS_CODE_REGEX));
        System.out.println("Byte length test: " + byteLen.matches(BYTE_LENGTH_REGEX));

    }
     */

}
