package filtering;

import filtering.process.Process;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;



public class Validator implements Process<String, List<String>>  {

    public static final String IP_REGEX = "((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}";
    public static final String DATE_REGEX = "(\\d{2})/(\\w{3})/(\\d{4}):(\\d{2}):(\\d{2}):(\\d{2})";
    public static final String HTTP_REQUEST_REGEX = "(GET|POST|PUT|DELETE|PATCH|HEAD|OPTIONS)";
    public static final String ENDPOINT_REGEX = "/\\w+";
    public static final String HTTP_VERSION_REGEX = "HTTP/\\d\\.\\d";
    public static final String STATUS_CODE_REGEX = "\\d{3}";
    public static final String BYTE_LENGTH_REGEX = "\\d+";
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
}
