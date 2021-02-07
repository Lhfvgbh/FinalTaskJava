import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

class Curriculum {
    @Getter
    @Setter
    @NonNull
    private String name;

    @Getter
    @Setter
    @NonNull
    private List<Course> courses;

    int getTotalTime() {
        int time = 0;
        for (Course c : courses) {
            time += c.courseDuration;
        }
        return time;
    }
}
