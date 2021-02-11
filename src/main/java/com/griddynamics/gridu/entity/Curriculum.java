package com.griddynamics.gridu.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

public class Curriculum {
    @Getter
    @Setter
    @NonNull
    private String name;

    @Getter
    @Setter
    @NonNull
    private List<Course> courses;

    public int getTotalTime() {
        int time = 0;
        for (Course c : courses) {
            time += c.courseDuration;
        }
        return time;
    }
}
