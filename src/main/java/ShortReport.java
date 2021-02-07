import java.text.ParseException;
import java.util.List;

/**
 * Short (Generating report date - 8 June 2020, Monday, 15:00) :
 * Ivanov Ivan (Java Developer) - Training is not finished. 1 d 3 hours are left until the end.
 * Sidorov Ivan (J2EE Developer) - Training completed. 3 hours have passed since the end.
 * *
 */


class ShortReport extends Report {
    @Override
    void generateReport() {
        try {
            StringBuilder output = new StringBuilder("Short (Generating report date - ")
                    .append(getCurrentTime())
                    .append(") :\n");

            List<InputRecords> records = readData();

            for (InputRecords record : records) {
                output.append(record.getStudent().getName())
                        .append(" (").append(record.getCurriculum().getName())
                        .append(") - ")
                        .append(record.getStatus());
                report.add(output.toString());
                output.setLength(0);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
