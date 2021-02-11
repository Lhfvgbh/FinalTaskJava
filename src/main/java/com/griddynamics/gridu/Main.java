package com.griddynamics.gridu;

import com.griddynamics.gridu.report.FullReport;
import com.griddynamics.gridu.report.Report;
import com.griddynamics.gridu.report.ShortReport;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

/**
 * Final Task
 * <p>
 * Implement a Program for students in a training center.  
 * Each student is trained according to an individual program.
 * The training program consists of a set of courses that a student must take sequentially.
 * Each course has a certain duration.
 * <p>
 * The program should allow one to:
 * 1. Determine - considering the current date - when the student will complete the course;
 * 2. Calculate how many days and hours the student still has before the end of the program or how many days and hours ago the student completed the course;
 * 3. Student data is set in code;
 * <p>
 * Requirements:
 * 1. Training is considered to be 5 working days a week, with an 8-hour school day from 10 to 18.
 * 2. The date of the launch option is carried out by the input parameter.
 * Information about the type of the report: short or full (no parameter or parameter 0 - an abbreviated view of the short report, otherwise - a full report.)
 * <p>
 * The following is a list of student data:
 * <p>
 * STUDENT: Ivanov Ivan
 * CURRICULUM: Java Developer
 * START_DATE: 1 June 2020 - Monday
 * COURSE                		DURATION (hrs)
 * --------------------------------------------
 * 1. Java.                         16        
 * 2. JDBC.                         24        
 * 3. Spring.                       16
 * <p>
 * STUDENT: Sidorov Ivan
 * CURRICULUM: AQE
 * START_DATE: 1 June 2020 - Monday
 * COURSE               		DURATION (hrs)
 * --------------------------------------------
 * 1. Test design.                  10
 * 2. Page Object.                  16                
 * 3. Selenium.                     16
 * <p>
 * Output: The calculation result should be displayed in the console with the name of the student and the program being studied.
 * <p>
 * Output examples:
 * Short (Generating report date - 8 June 2020, Monday, 15:00) :
 * * 		Ivanov Ivan (Java Developer) - Training is not finished. 1 d 3 hours are left until the end.
 * * 		Sidorov Ivan (J2EE Developer) - Training completed. 3 hours have passed since the end.
 * <p>
 * Full information:
 * * 		This report should contain:
 * * 		student name
 * * 		working time (from 10 to 18)
 * * 		program name
 * * 		program duration
 * * 		hours
 * * 		start date
 * * 		end date
 * * 		how much time has passed / remains until completion.
 */


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Type no parameter or parameter 0 for short report or else for full one:");
        String type = scanner.nextLine();

        System.out.println("Enter data for a report in format \"d MMMM yyyy HH:mm\" or leave empty for current date:");
        String date = scanner.nextLine();

        try {
            Report report;

            if (type.equals(" ") || type.equals("0") || type.equals("")) {
                report = date.equals("") ? new ShortReport(new Date()) : new ShortReport(date);
            } else {
                report = date.equals("") ? new FullReport(new Date()) : new FullReport(date);
            }

            report.generateReport();
            report.printReport();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
