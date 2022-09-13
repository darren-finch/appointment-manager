package com.darrenfinch.appointmentmanager.screens.reports;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NumberOfAppointmentsForCustomer {
    private final IntegerProperty customerId = new SimpleIntegerProperty();
    private final StringProperty customerName = new SimpleStringProperty();
    private final IntegerProperty numberOfAppointments = new SimpleIntegerProperty();

    public NumberOfAppointmentsForCustomer(int customerId, String customerName, int numberOfAppointments) {
        this.customerId.set(customerId);
        this.customerName.set(customerName);
        this.numberOfAppointments.set(numberOfAppointments);
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public int getNumberOfAppointments() {
        return numberOfAppointments.get();
    }

    public IntegerProperty numberOfAppointmentsProperty() {
        return numberOfAppointments;
    }

    public void setNumberOfAppointments(int numberOfAppointments) {
        this.numberOfAppointments.set(numberOfAppointments);
    }
}