import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

class InputRecords {
    @Getter
    Student student;
    @Getter
    Date startDate;
    @Getter
    Date endDate;
    @Getter
    Curriculum curriculum;
    @Getter
    String status;

    InputRecords(String record) throws ParseException {
        curriculum = new Curriculum();
        String[] data = record.split("\n");
        for (int i = 0; i < data.length; i++) {
            if (data[i].contains("STUDENT: ")) {
                this.student = new Student(data[i].replace("STUDENT: ", ""));
            } else if (data[i].contains("START_DATE: ")) {
                this.startDate = new SimpleDateFormat("d MMMM yyyy - EEEE").parse(data[i].replace("START_DATE: ", ""));
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
        setEndDate(startDate, curriculum.getTotalTime());
        setStatus(endDate);
    }

    private void setEndDate(Date startDate, int duration) {
        int durationDays = (int) Math.ceil(duration / 8.0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        for (int i = 1; i < durationDays; i++) {
            calendar.add(Calendar.DATE, 1);
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                durationDays++;
            }
        }

        calendar.add(Calendar.HOUR, 18);
        this.endDate = calendar.getTime();
    }

    private void setStatus(Date endDate) {
        long endTimeInMills = endDate.getTime();
        long currentTimeInMills = new Date().getTime();

        int hoursTotal = Math.abs((int) ((endTimeInMills - currentTimeInMills) / 1000 / 60 / 60));
        int days = hoursTotal / 24;
        int hours = hoursTotal - days * 24;

        if (currentTimeInMills < endTimeInMills) {
            this.status = String.format("Training is not finished. %d d %d h hours are left until the end.", days, hours);
        } else {
            this.status = String.format("Training completed. %s d %s h hours have passed since the end.", days, hours);
        }
    }
}
