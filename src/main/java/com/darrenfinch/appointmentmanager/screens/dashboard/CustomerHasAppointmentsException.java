package com.darrenfinch.appointmentmanager.screens.dashboard;

public class CustomerHasAppointmentsException extends Exception {
    private final int customerId;

    /**
     * Constructs a new CustomerHasAppointmentsException. The customer id is used for the message display.
     */
    public CustomerHasAppointmentsException(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the message of this exception.
     */
    @Override
    public String getMessage() {
        return "Appointments still exist for customer with ID " + customerId + ". Please delete these appointments before deleting the customer.";
    }
}
