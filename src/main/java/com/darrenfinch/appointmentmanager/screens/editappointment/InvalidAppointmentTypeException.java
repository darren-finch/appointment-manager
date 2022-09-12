package com.darrenfinch.appointmentmanager.screens.editappointment;

public class InvalidAppointmentTypeException extends Exception {

    private final int appointmentId;

    public InvalidAppointmentTypeException(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public String getMessage() {
        return "The loaded appointment did not have a valid appointment type, resetting to default value.";
    }
}
