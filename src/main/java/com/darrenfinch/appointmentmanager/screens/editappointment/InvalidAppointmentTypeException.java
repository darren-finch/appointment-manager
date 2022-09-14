package com.darrenfinch.appointmentmanager.screens.editappointment;

public class InvalidAppointmentTypeException extends Exception {

    private final int appointmentId;

    /**
     * Constructs a new InvalidAppointmentTypeException. The appointment id is used for the message display.
     */
    public InvalidAppointmentTypeException(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets the message of this exception.
     */
    @Override
    public String getMessage() {
        return "The loaded appointment did not have a valid appointment type, resetting to default value.";
    }
}
