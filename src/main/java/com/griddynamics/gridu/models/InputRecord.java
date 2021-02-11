package com.griddynamics.gridu.models;

import com.griddynamics.gridu.models.TimeStamp;
import com.griddynamics.gridu.entity.Course;
import com.griddynamics.gridu.entity.Curriculum;
import com.griddynamics.gridu.entity.Student;
import lombok.Getter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * STUDENT: Sidorov Ivan
 * CURRICULUM: AQE
 * START_DATE: 1 June 2020 - Monday
 * COURSE               		DURATION (hrs)
 * --------------------------------------------
 * 1. Test design.                  10
 * 2. Page Object.                  16                
 * 3. Selenium.                     16
 */

public class InputRecord {
    @Getter
    Student student;
    @Getter
    Curriculum curriculum;
    @Getter
    Date startDate;
    @Getter
    String endDate;
    @Getter
    String status;

    private Date date;

    public InputRecord(String record, String currentTime) throws ParseException {
        curriculum = new Curriculum();
        String[] data = record.split("\n");
        for (int i = 0; i < data.length; i++) {
            if (data[i].contains("STUDENT: ")) {
                this.student = new Student(data[i].replace("STUDENT: ", ""));
            } else if (data[i].contains("START_DATE: ")) {
                //this.startDate = new SimpleDateFormat(Constants.OUTPUT_DATE_FORMAT).parse(data[i].replace("START_DATE: ", ""));
                this.startDate = TimeStamp.OUTPUT_DATE_FORMAT.getDate(data[i].replace("START_DATE: ", ""));
            } else if (data[i].contains("CURRICULUM: ")) {
                this.curriculum.setName(data[i].replace("CURRICULUM: ", ""));
            } else if (data[i].contains("--------------------------------------------")) {
                List<Course> courses = new ArrayList<>();
                for (int j = i + 1; j < (data.length); j++) {
                    int duration = Integer.parseInt(data[j].split("\t\t")[1].replace("\u00A0", ""));
                    String name = data[j].split("\t\t")[0].trim();
                    courses.add(new Course(name, duration));
                }
                this.curriculum.setCourses(courses);
                break;
            }
        }
        this.endDate = calculateEndDate(this.curriculum.getTotalTime());
        this.status = calculateStatus(currentTime);
    }

    private String calculateEndDate(int duration) {
        int durationDays = (int) Math.ceil(duration / 8.0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.startDate);

        for (int i = 1; i < durationDays; i++) {
            calendar.add(Calendar.DATE, 1);
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                durationDays++;
            }
        }

        calendar.add(Calendar.HOUR, 18);
        this.date = calendar.getTime();
        return TimeStamp.OUTPUT_DATE_FORMAT.getDateString(this.date);
    }

    private String calculateStatus(String currentTime) throws ParseException {
        long endTimeInMills = this.date.getTime();
        long currentTimeInMills = (TimeStamp.INPUT_DATE_TIME_FORMAT.getDate(currentTime)).getTime();

        int hoursTotal = Math.abs((int) ((endTimeInMills - currentTimeInMills) / 1000 / 60 / 60));
        int days = hoursTotal / 24;
        int hours = hoursTotal - days * 24;

        String status;
        if (currentTimeInMills < endTimeInMills) {
            status = String.format("Training is not finished. %d d %d h hours are left until the end.", days, hours);
        } else {
            status = String.format("Training completed. %s d %s h hours have passed since the end.", days, hours);
        }
        return status;
    }

}
