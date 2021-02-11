package com.griddynamics.gridu;

import com.griddynamics.gridu.models.TimeStamp;
import com.griddynamics.gridu.models.InputRecord;
import com.griddynamics.gridu.report.Report;
import com.griddynamics.gridu.report.ShortReport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ReportTest {
    private InputRecord recordFinished;
    private InputRecord recordProcessing;
    private String currentTimeReport;

    @Before
    public void createInputRecord() throws ParseException {
        String currentTimeInput = "10 February 2021, Wednesday, 17:20";
        currentTimeReport = "10 February 2021 17:20";
        recordFinished = new InputRecord("STUDENT: Sidorov Ivan\n" +
                "CURRICULUM: AQE\n" +
                "START_DATE: 1 June 2020 - Monday\n" +
                "COURSE               \t\tDURATION (hrs)\n" +
                "--------------------------------------------\n" +
                "1. Test design.                \t\t10\n" +
                "2. Page Object.                \t\t16                \n" +
                "3. Selenium.                \t\t16", currentTimeInput);
        recordProcessing = new InputRecord("STUDENT: Ivanov Ivan\n" +
                "CURRICULUM: Java Developer\n" +
                "START_DATE: 8 Feb 2021 - Monday\n" +
                "COURSE                \t\tDURATION (hrs)\n" +
                "--------------------------------------------\n" +
                "1. Java.                \t\t16        \n" +
                "2. JDBC.                \t\t24        \n" +
                "3. Spring.                \t\t16", currentTimeInput);
    }

    @Test
    public void checkReportStatus() {
        Assert.assertEquals("Training completed. 246 d 23 h hours have passed since the end.", recordFinished.getStatus());
        Assert.assertEquals("Training is not finished. 6 d 0 h hours are left until the end.", recordProcessing.getStatus());
    }


    @Test(expected = ParseException.class)
    public void checkUnparsedDate() throws ParseException {
        Assert.assertEquals(new GregorianCalendar(2021, Calendar.FEBRUARY, 10, 17, 20).getTime(), TimeStamp.OUTPUT_DATE_FORMAT.getDate(currentTimeReport));
    }

    @Test
    public void checkParsedDate() throws ParseException {
        Assert.assertEquals("1 June 2020 - Monday", TimeStamp.OUTPUT_DATE_FORMAT.getDateString(recordFinished.getStartDate()));
        Assert.assertEquals("8 February 2021 - Monday", TimeStamp.OUTPUT_DATE_FORMAT.getDateString(recordProcessing.getStartDate()));

        Assert.assertEquals(new GregorianCalendar(2020, Calendar.JUNE, 8).getTime(), TimeStamp.OUTPUT_DATE_FORMAT.getDate(recordFinished.getEndDate()));
        Assert.assertEquals(new GregorianCalendar(2021, Calendar.FEBRUARY, 16).getTime(), TimeStamp.OUTPUT_DATE_FORMAT.getDate(recordProcessing.getEndDate()));
    }

    @Test
    public void checkShortReportWithDate() throws ParseException {
        String expectedReport = "Short (Generating report date - 10 February 2021, Wednesday, 17:20) :\n" +
                "Ivanov Ivan (Java Developer) - Training completed. 245 d 23 h hours have passed since the end.\n" +
                "Ivanov Ivan (Java Developer) - Training is not finished. 6 d 0 h hours are left until the end.\n" +
                "Sidorov Ivan (AQE) - Training is not finished. 5 d 0 h hours are left until the end.\n" +
                "Sidorov Ivan (AQE) - Training completed. 188 d 23 h hours have passed since the end.\n" +
                "Sidorov Ivan (AQE) - Training completed. 246 d 23 h hours have passed since the end.\n" +
                "Ivanov Ivan (Java Developer) - Training completed. 4 d 23 h hours have passed since the end.\n";

        Report actualReport = new ShortReport(currentTimeReport);
        actualReport.generateReport();
        StringBuilder sb = new StringBuilder();
        for (String record : actualReport.getReport()) {
            sb.append(record).append("\n");
        }

        Assert.assertEquals(expectedReport, sb.toString());
    }
}
