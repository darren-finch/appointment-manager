package com.darrenfinch.appointmentmanager.common.data.misc;

/**
 * This is used in several places in the UI to filter dates by the current week, month, or by all time.
 */
public enum TimeFilter {
    WEEK("This week"),
    MONTH("This month"),
    ALL_TIME("All Time");

    private final String privateName;

    TimeFilter(String name) {
        this.privateName = name;
    }

    @Override
    public String toString() {
        return privateName;
    }
}
