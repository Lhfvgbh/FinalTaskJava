import lombok.Getter;
import lombok.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

abstract class Report {

    @NonNull
    List<String> report = new ArrayList<>();
    @Getter
    private String currentTime;

    Report() {
        this.currentTime = new SimpleDateFormat("d MMMM yyyy, EEEE, HH:mm").format(new Date());
    }

    abstract void generateReport();

    void printReport() {
        for (String record : report) {
            System.out.println(record);
        }
    }

    List<InputRecords> readData() throws ParseException {
        List<InputRecords> records = new ArrayList<>();
        StringBuilder report = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(getClass().getClassLoader().getResource("StudentList.txt").toURI()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                report.append(line).append("\n");
                if (line.equals("")) {
                    InputRecords record = new InputRecords(report.toString());
                    records.add(record);
                    report.setLength(0);
                }
            }
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return records;
    }

}
