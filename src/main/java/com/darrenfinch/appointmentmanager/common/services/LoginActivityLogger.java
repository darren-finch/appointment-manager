package com.darrenfinch.appointmentmanager.common.services;

import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class LoginActivityLogger {
    /**
     * Logs an attempted login to a file in the project root directory called "login_activity.txt".
     */
    public void writeLoginAttempt(String userName, ZonedDateTime timeStamp, boolean loginSuccessful) {
        try (FileWriter writer = new FileWriter("login_activity.txt", true);) {
            writer.append("Attempted ")
                    .append(loginSuccessful ? "successful" : "unsuccessful")
                    .append(" login at ")
                    .append(timeStamp.format(DateTimeFormatter.ISO_DATE_TIME))
                    .append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
