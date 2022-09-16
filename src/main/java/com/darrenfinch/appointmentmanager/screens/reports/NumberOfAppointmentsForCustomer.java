package com.darrenfinch.appointmentmanager.screens.reports;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NumberOfAppointmentsForCustomer {
    private final IntegerProperty customerId = new SimpleIntegerProperty();
    private final StringProperty customerName = new SimpleStringProperty();
    private final IntegerProperty numberOfAppointments = new SimpleIntegerProperty();

    /**
     * Constructs a NumberOfAppointmentsForCustomer with some starting values for its properties.
     */
    public NumberOfAppointmentsForCustomer(int customerId, String customerName, int numberOfAppointments) {
        this.customerId.set(customerId);
        this.customerName.set(customerName);
        this.numberOfAppointments.set(numberOfAppointments);
    }

    /**
     * Gets the customer id value from the customer id property.
     */
    public int getCustomerId() {
        return customerId.get();
    }

    /**
     * Gets the customer id property.
     */
    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    /**
     * Gets the customer name value from the customer name property.
     */
    public String getCustomerName() {
        return customerName.get();
    }

    /**
     * Gets the customer name property.
     */
    public StringProperty customerNameProperty() {
        return customerName;
    }

    /**
     * Gets the number of appointments value from the number of appointments property.
     */
    public int getNumberOfAppointments() {
        return numberOfAppointments.get();
    }

    /**
     * Gets the number of appointments property.
     */
    public IntegerProperty numberOfAppointmentsProperty() {
        return numberOfAppointments;
    }
}