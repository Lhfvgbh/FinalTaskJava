import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
class Course {
    @Getter
    String courseName;
    @Getter
    int courseDuration;
}
