package com.griddynamics.gridu.report;

import com.griddynamics.gridu.models.InputRecord;
import com.griddynamics.gridu.models.TimeStamp;
import lombok.Getter;
import lombok.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Report {

    @NonNull
    @Getter
    List<String> report = new ArrayList<>();
    @Getter
    private String currentTime;


    Report(Date date) {
        this.currentTime = TimeStamp.INPUT_DATE_TIME_FORMAT.getDateString(date);
        System.out.println("no args constructor");
    }

    Report(String dateString) throws ParseException {
        this(TimeStamp.INPUT_DATE_FORMAT.getDate(dateString));
    }

    public abstract void generateReport() throws ParseException;

    public void printReport() {
        for (String record : report) {
            System.out.println(record);
        }
    }

    List<InputRecord> readData() throws ParseException {
        List<InputRecord> records = new ArrayList<>();
        StringBuilder report = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(getClass().getClassLoader().getResource("StudentList.txt").toURI()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                report.append(line).append("\n");
                if (line.equals("")) {
                    InputRecord record = new InputRecord(report.toString(), this.currentTime);
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
