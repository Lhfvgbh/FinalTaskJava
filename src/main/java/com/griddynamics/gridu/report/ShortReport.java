package com.griddynamics.gridu.report;

import com.griddynamics.gridu.models.InputRecord;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Short (Generating report date - 8 June 2020, Monday, 15:00) :
 * Ivanov Ivan (Java Developer) - Training is not finished. 1 d 3 hours are left until the end.
 * Sidorov Ivan (J2EE Developer) - Training completed. 3 hours have passed since the end.
 * *
 */

public class ShortReport extends Report {
    public ShortReport(String date) throws ParseException {
        super(date);
    }

    public ShortReport(Date date) {
        super(date);
    }

    @Override
    public void generateReport() throws ParseException {
        StringBuilder output = new StringBuilder("Short (Generating report date - ")
                .append(getCurrentTime())
                .append(") :\n");

        List<InputRecord> records = readData();

        for (InputRecord record : records) {
            output.append(record.getStudent().getName())
                    .append(" (")
                    .append(record.getCurriculum().getName())
                    .append(") - ")
                    .append(record.getStatus());
            report.add(output.toString());
            output.setLength(0);
        }
    }
}
