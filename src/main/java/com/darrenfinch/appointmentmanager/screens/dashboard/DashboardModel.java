package com.darrenfinch.appointmentmanager.screens.dashboard;

import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DashboardModel {
    private final ObjectProperty<ObservableList<Customer>> customersProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<DashboardController.ViewByTimeFrame> viewByProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<Appointment>> appointmentsProperty = new SimpleObjectProperty<>();

    public DashboardModel() {
        customersProperty.set(FXCollections.emptyObservableList());
        viewByProperty.set(DashboardController.ViewByTimeFrame.WEEK);
        appointmentsProperty.set(FXCollections.emptyObservableList());
    }

    public ObjectProperty<ObservableList<Customer>> getCustomersProperty() {
        return customersProperty;
    }

    public Property<DashboardController.ViewByTimeFrame> viewByProperty() {
        return viewByProperty;
    }

    public DashboardController.ViewByTimeFrame getViewBy() {
        return viewByProperty.get();
    }

    public ObjectProperty<ObservableList<Appointment>> getAppointmentsProperty() {
        return appointmentsProperty;
    }
}
