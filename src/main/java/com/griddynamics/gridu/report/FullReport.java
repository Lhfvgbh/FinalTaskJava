package com.griddynamics.gridu.report;

import com.griddynamics.gridu.models.InputRecord;
import com.griddynamics.gridu.models.TimeStamp;
import com.griddynamics.gridu.entity.Course;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Full information:
 * This report should contain:
 * student name
 * working time (from 10 to 18)
 * program name
 * program duration
 * hours
 * start date
 * end date
 * how much time has passed / remains until completion.
 */

public class FullReport extends Report {

    public FullReport(String date) throws ParseException{
        super(date);
    }

    public FullReport(Date date) {
        super(date);
    }

    @Override
    public void generateReport() throws ParseException {
            StringBuilder output = new StringBuilder("Full information (Generating report date - ")
                    .append(getCurrentTime())
                    .append(") :\n");

            List<InputRecord> records = readData();

            for (InputRecord record : records) {
                output.append("\nSTUDENT: ").append(record.getStudent().getName())
                        .append("\nWORKING HOURS: 10:00-18:00")
                        .append("\nCURRICULUM NAME: ").append(record.getCurriculum().getName())
                        .append("\nCURRICULUM DURATION IN HOURS: ")
                        .append(record.getCurriculum().getTotalTime())
                        .append("\nCURRICULUM COURSES: ");

                for (Course c : record.getCurriculum().getCourses()) {
                    output.append("\nCOURSE NAME: ")
                            .append(c.getCourseName())
                            .append("\nCOURSE DURATION: ")
                            .append(c.getCourseDuration());
                }

                output.append("\nSTART DATE: ")
                        .append(TimeStamp.OUTPUT_DATE_FORMAT.getDateString(record.getStartDate()))
                        .append("\nEND DATE: ")
                        .append(record.getEndDate())
                        .append("\nSTATUS: ")
                        .append(record.getStatus())
                        .append("\n--------------------------------------------");

                report.add(output.toString());
                output.setLength(0);
            }
    }
}