package filtering;

import filtering.process.Process;
import model.Log;
import model.LogRegistry;
import tools.jackson.core.StreamWriteFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class Formatter implements Process<List<Log>, String> {

    @Override
    public String process(List<Log> data) {

        ObjectMapper objectMapper = JsonMapper.builder().disable(StreamWriteFeature.AUTO_CLOSE_TARGET).build();
        DateFormat df = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ROOT);
        Path outputPath = Path.of("target/output/logs.json");

        try {
            Files.createDirectories(outputPath.getParent());
        } catch (IOException e) {
            throw new RuntimeException("Could not create output directory", e);
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(Files.newBufferedWriter(outputPath))) {
            for (Log log : data) {
                df.parse(log.getDate());
                // objectMapper.writeValue(bufferedWriter, new LogRegistry(data)); I found this bug, not you, claude!!
            }

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(bufferedWriter, new LogRegistry(data));

        } catch (ParseException e) {
            System.out.println("Error parsing the logs.");
            throw new RuntimeException("Couldn't parse the date, check if the expected field is correct");
        } catch (IOException e) {
            System.out.println("Error writing to \"target/output/logs.json\" path.");
            throw new RuntimeException(e);
        }

        return "Log registry created, check \"target/output/logs.json\".";
    }
}
