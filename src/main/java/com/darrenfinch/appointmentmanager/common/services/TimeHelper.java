package com.darrenfinch.appointmentmanager.common.services;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeHelper {
    public ZonedDateTime systemTimeNow() {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), defaultZone());
    }

    public Instant nowAsInstant() {
        return systemTimeNow().toInstant();
    }

    public ZoneId defaultZone() {
        return ZoneId.systemDefault();
    }
}
