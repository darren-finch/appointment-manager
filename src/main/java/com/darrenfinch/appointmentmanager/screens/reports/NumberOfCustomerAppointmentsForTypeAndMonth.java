package com.darrenfinch.appointmentmanager.screens.reports;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NumberOfCustomerAppointmentsForTypeAndMonth {
    private final IntegerProperty numberOfAppointments = new SimpleIntegerProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final StringProperty month = new SimpleStringProperty();

    public NumberOfCustomerAppointmentsForTypeAndMonth(int numberOfAppointments, String type, String month) {
        this.numberOfAppointments.set(numberOfAppointments);
        this.type.set(type);
        this.month.set(month);
    }

    public int getNumberOfAppointments() {
        return numberOfAppointments.get();
    }

    public IntegerProperty numberOfAppointmentsProperty() {
        return numberOfAppointments;
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public String getMonth() {
        return month.get();
    }

    public StringProperty monthProperty() {
        return month;
    }

    public void setNumberOfAppointments(int numberOfAppointments) {
        this.numberOfAppointments.set(numberOfAppointments);
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setMonth(String month) {
        this.month.set(month);
    }
}
