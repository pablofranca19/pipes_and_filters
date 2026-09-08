package filtering;

import filtering.process.Process;

import java.util.ArrayList;
import java.util.List;



// this class needs to receive the list of strings, and transform them into a list of Log objects
public class Selector implements Process<List<String>, List<Log>> {

    private final List<String> list;

    public Selector(List<String> list) {
        this.list = list;
        process(this.list);
    }


    @Override
    public List<Log> process(List<String> data) {
        List<Log> errorLogs = convertToLogObject(data);

        for (Log log : errorLogs) {
            if (log.getStatusCode().startsWith("4") || log.getStatusCode().startsWith("5")) {
                errorLogs.add(log);
            }
        }

        return errorLogs;
    }

    public List<Log> convertToLogObject(List<String> data) {
        List<String> formattedLine = new ArrayList<>();
        List<Log> logList = new ArrayList<>();

        for (String line : data) {
            String result = line.replaceAll("[\\-\"]", "");
            formattedLine.add(result);
        }

        for (String str : formattedLine) {
            String[] logs = str.split("\\s+");
            logList.add(new Log(logs[0], logs[1], logs[2], logs[3], logs[4], logs[5], logs[6]));
        }

        return logList;
    }
}
