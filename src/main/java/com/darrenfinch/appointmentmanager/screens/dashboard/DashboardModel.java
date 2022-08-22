package com.darrenfinch.appointmentmanager.screens.dashboard;

import com.darrenfinch.appointmentmanager.data.models.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DashboardModel {
    private final ObservableList<Customer> customersProperty = FXCollections.emptyObservableList();
    private final StringProperty viewByProperty = new SimpleStringProperty();
    private final ObservableList<Customer> appointmentsProperty = FXCollections.emptyObservableList();

    public DashboardModel() {
        viewByProperty.set("");
    }

    public ObservableList<Customer> getCustomersProperty() {
        return customersProperty;
    }

    public StringProperty viewByProperty() {
        return viewByProperty;
    }

    public ObservableList<Customer> getAppointmentsProperty() {
        return appointmentsProperty;
    }
}
