package com.griddynamics.gridu.models;

import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public enum TimeStamp {

    INPUT_DATE_FORMAT("d MMMM yyyy HH:mm"),
    OUTPUT_DATE_FORMAT("d MMMM yyyy - EEEE"),
    INPUT_DATE_TIME_FORMAT("d MMMM yyyy, EEEE, HH:mm");

    @Getter
    private String format;

    TimeStamp(String format) {
        this.format = format;
    }

    public Date getDate(String date) throws ParseException {
        return new SimpleDateFormat(format).parse(date);
    }

    public String getDateString(Date date) {
        return new SimpleDateFormat(format).format(date);
    }
}
