package com.griddynamics.gridu.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Course {
    @Getter
    String courseName;
    @Getter
    int courseDuration;
}
