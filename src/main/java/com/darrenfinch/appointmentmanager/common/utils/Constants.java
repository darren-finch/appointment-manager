package com.darrenfinch.appointmentmanager.common.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Constants {
    /**
     * The numerical offset of the database's time zone (should always be UTC).
     */
    public static final String DATABASE_TIME_ZONE_OFFSET = "+00:00";

    /**
     * The base name of the resource bundle for this application.
     */
    public static final String RESOURCE_BUNDLE_BASE_NAME = "ApplicationManager";

    /**
     * The threshold in minutes for alerting the user of upcoming appointments
     */
    public static final int APPOINTMENT_ALERT_THRESHOLD_MINUTES = 15;

    /**
     * Passing this ID to an edit form tells the form that we are interested in adding a new entity, not updating an existing one.
     */
    public static final int INVALID_ID = -1;

    /**
     * When the business opens. Used to validate appointment times.
     */
    public static final LocalTime BUSINESS_START_TIME = LocalTime.of(8, 0);

    /**
     * When the business closes. Used to validate appointment times.
     */
    public static final LocalTime BUSINESS_END_TIME = LocalTime.of(22, 0);

    /**
     * The standard format for all dates without a time to be displayed in.
     */
    public static final String STANDARD_DATE_FORMAT = "MM-dd-yyyy";

    /**
     * The standard format for all times without a date to be displayed in.
     */
    public static final String STANDARD_TIME_FORMAT = "hh:mm a";

    /**
     * The standard format for all dates with a time to be displayed in.
     */
    public static final String STANDARD_DATE_TIME_FORMAT = STANDARD_DATE_FORMAT + " " + STANDARD_TIME_FORMAT;

    /**
     * The names of all the months on a standard Gregorian calendar.
     * There are other methods to get the available months, but this is the simplest.
     */
    public static final ObservableList<String> MONTHS = FXCollections.observableList(List.of(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    ));

    /**
     * Gets a list of strings representing valid hour values in the 12 HR time format.
     */
    public static List<String> getHours() {
        ArrayList<String> hours = new ArrayList<>(12);
        for (int i = 1; i <= 12; i++) {
            hours.add(String.format("%02d", i));
        }
        return hours;
    }

    /**
     * Gets a list of strings representing valid minute values in the 12 HR time format.
     */
    public static List<String> getMinutes() {
        ArrayList<String> minutes = new ArrayList<>(60);
        for (int i = 0; i < 60; i++) {
            minutes.add(String.format("%02d", i));
        }
        return minutes;
    }

    /**
     * Represents AM or PM
     */
    public static final List<String> AM_OR_PM = List.of("AM", "PM");
}