package com.darrenfinch.appointmentmanager.common.services;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeHelper {

    /**
     * Use this instead of <code>ZonedDateTime.now()</code> because while the <code>ZonedDateTime.now()</code>
     * method returns the current time in the local time zone, yes, it does not account for the local time set on the user's
     * computer, which could be different than the time set for the time zone in gene
     */
    public ZonedDateTime systemTimeNow() {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), defaultZone());
    }

    /**
     * A shorthand for <code>systemTimeNow().toInstant()</code>.
     */
    public Instant nowAsInstant() {
        return systemTimeNow().toInstant();
    }

    /**
     * Returns the default zone that the user is in.
     */
    public ZoneId defaultZone() {
        return ZoneId.systemDefault();
    }
}
