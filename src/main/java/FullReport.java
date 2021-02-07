import java.text.ParseException;
import java.text.SimpleDateFormat;
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

class FullReport extends Report {

    @Override
    void generateReport() {
        try {
            StringBuilder output = new StringBuilder("Full information (Generating report date - ")
                    .append(getCurrentTime())
                    .append(") :\n");

            List<InputRecords> records = readData();

            for (InputRecords record : records) {
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
                        .append(new SimpleDateFormat("d MMMM yyyy - EEEE").format(record.getStartDate()))
                        .append("\nEND DATE: ")
                        .append(new SimpleDateFormat("d MMMM yyyy - EEEE").format(record.getEndDate()))
                        .append("\nSTATUS: ")
                        .append(record.getStatus())
                        .append("\n--------------------------------------------");

                report.add(output.toString());
                output.setLength(0);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}