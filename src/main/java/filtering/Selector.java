package filtering;

import filtering.process.Process;
import model.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


// this class needs to receive the list of strings, and transform them into a list of Log objects
public class Selector implements Process<List<String>, List<Log>> {

    @Override
    public List<Log> process(List<String> data) {
        List<Log> logs = convertToLogObject(data);
        List<Log> errorLogs = new ArrayList<>();

        for (Log log : logs) {
            if (log.getStatusCode().startsWith("4") || log.getStatusCode().startsWith("5")) {
                errorLogs.add(log);
            }
        }

        return errorLogs;
    }

    public List<Log> convertToLogObject(List<String> data) {
        List<String> formattedLine = new ArrayList<>();
        List<Log> logList = new ArrayList<>();
        Pattern regex = Pattern.compile("[\\-\"\\[\\]]");
        Pattern whitespaceRegex = Pattern.compile("\\s+");

        for (String line : data) {
            String result = line.replaceAll(regex.pattern(), "");
            formattedLine.add(result);
        }

        for (String str : formattedLine) {
            String[] logs = str.split(whitespaceRegex.pattern());
            logList.add(new Log(logs[0], logs[1], logs[2], logs[3], logs[4], logs[5], logs[6]));
        }

        return logList;
    }
}
