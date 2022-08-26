package com.darrenfinch.appointmentmanager.screens.dashboard;

import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DashboardModel {
    private final ObjectProperty<ObservableList<Customer>> customersProperty = new SimpleObjectProperty<>();
    private final StringProperty viewByProperty = new SimpleStringProperty();
    private final ObjectProperty<ObservableList<Appointment>> appointmentsProperty = new SimpleObjectProperty<>();

    public DashboardModel() {
        customersProperty.set(FXCollections.emptyObservableList());
        viewByProperty.set("");
        appointmentsProperty.set(FXCollections.emptyObservableList());
    }

    public ObjectProperty<ObservableList<Customer>> getCustomersProperty() {
        return customersProperty;
    }

    public StringProperty viewByProperty() {
        return viewByProperty;
    }

    public ObjectProperty<ObservableList<Appointment>> getAppointmentsProperty() {
        return appointmentsProperty;
    }
}
