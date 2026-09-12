package application;

import filtering.Anonymizer;
import filtering.Formatter;
import filtering.Selector;
import filtering.Validator;
import filtering.process.Process;
import model.Log;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main  {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    // mutes an issue
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        run();
        logger.log(Level.INFO, "Program initialized.");
    }




    private static void run () {
        Process<String, List<String>> validator = new Validator();
        Process<List<String>, List<Log>> selector = new Selector();
        Process<List<Log>, List<Log>> anonymizer = new Anonymizer();
        Process<List<Log>, String> formatter = new Formatter();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the log file path: ");
        String path = scanner.nextLine();
        scanner.close();

        logger.log(Level.INFO, "File logging initialized");

        List<String> validLines = validator.process(path);
        List<Log> selectedLogs = selector.process(validLines);
        List<Log> anonymizedLogs = anonymizer.process(selectedLogs);
        String result = formatter.process(anonymizedLogs);

        logger.log(Level.INFO, result);
    }
}
