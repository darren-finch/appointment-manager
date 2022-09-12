package com.darrenfinch.appointmentmanager.screens.dashboard;

public class CustomerHasAppointmentsException extends Exception {
    private final int customerId;

    public CustomerHasAppointmentsException(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String getMessage() {
        return "Appointments still exist for customer with ID " + customerId + ". Please delete these appointments before deleting the customer.";
    }
}
