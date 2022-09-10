package com.darrenfinch.appointmentmanager.common.utils;

import java.io.File;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Constants {
    public static final String RESOURCE_BUNDLE_BASE_NAME = "ApplicationManager";

    public static final int APPOINTMENT_ALERT_THRESHOLD_MINUTES = 15;

    public static final int INVALID_ID = -1;

    public static final LocalTime BUSINESS_START_TIME = LocalTime.of(8, 0);
    public static final LocalTime BUSINESS_END_TIME = LocalTime.of(22, 0);

    public static final String STANDARD_DATE_FORMAT = "MM-dd-yyyy";
    public static final String STANDARD_TIME_FORMAT = "hh:mm a";

    public static final String STANDARD_DATE_TIME_FORMAT = STANDARD_DATE_FORMAT + " " + STANDARD_TIME_FORMAT;

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

    public static void main(String[] args) {
        File file = new File(".");
        System.out.println(file.getAbsolutePath());
    }
}