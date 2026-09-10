package application;

import filtering.Anonymizer;
import filtering.Formatter;
import filtering.Selector;
import filtering.Validator;
import filtering.process.Process;
import model.Log;

import java.util.List;


public class Main  {
    static void main(String[] args) {

        String path = "src/logs/logs.txt";

        Process<String, List<String>> validator = new Validator();
        Process<List<String>, List<Log>> selector = new Selector();
        Process<List<Log>, List<Log>> anonymizer = new Anonymizer();
        Process<List<Log>, String> formatter = new Formatter();

        List<String> validLines = validator.process(path);
        List<Log> selectedLogs = selector.process(validLines);
        List<Log> anonymizedLogs = anonymizer.process(selectedLogs);
        String result = formatter.process(anonymizedLogs);

        System.out.println(result);


    }
}
