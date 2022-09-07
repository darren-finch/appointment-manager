package com.darrenfinch.appointmentmanager.common.utils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final int INVALID_ID = -1;

    public static final LocalTime BUSINESS_START_TIME = LocalTime.of(8, 0, 0);
    public static final LocalTime BUSINESS_END_TIME = LocalTime.of(22, 0, 0);

    public static final String STANDARD_DATE_FORMAT = "MM-dd-yyyy HH:mm a";

    public static List<String> getHours() {
        ArrayList<String> hours = new ArrayList<>(12);
        for (int i = 1; i <= 12; i++) {
            hours.add(String.format("%02d", i));
        }
        return hours;
    }

    public static List<String> getMinutes() {
        ArrayList<String> minutes = new ArrayList<>(60);
        for (int i = 0; i < 60; i++) {
            minutes.add(String.format("%02d", i));
        }
        return minutes;
    }

    public static final List<String> amOrPm = List.of("AM", "PM");
}