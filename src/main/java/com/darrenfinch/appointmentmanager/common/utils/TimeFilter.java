package com.darrenfinch.appointmentmanager.common.utils;

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
