package com.darrenfinch.appointmentmanager.screens.dashboard;

import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DashboardModel {
    private final ObjectProperty<ObservableList<CustomerWithLocationData>> customers = new SimpleObjectProperty<>();
    private final ObjectProperty<DashboardController.ViewByTimeFrame> viewBy = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<Appointment>> appointments = new SimpleObjectProperty<>();

    public DashboardModel() {
        customers.set(FXCollections.emptyObservableList());
        viewBy.set(DashboardController.ViewByTimeFrame.WEEK);
        appointments.set(FXCollections.emptyObservableList());
    }

    public ObservableList<CustomerWithLocationData> getCustomers() {
        return customers.get();
    }

    public ObjectProperty<ObservableList<CustomerWithLocationData>> customersProperty() {
        return customers;
    }

    public DashboardController.ViewByTimeFrame getViewBy() {
        return viewBy.get();
    }

    public ObjectProperty<DashboardController.ViewByTimeFrame> viewByProperty() {
        return viewBy;
    }

    public ObservableList<Appointment> getAppointments() {
        return appointments.get();
    }

    public ObjectProperty<ObservableList<Appointment>> appointmentsProperty() {
        return appointments;
    }

    public void setCustomers(ObservableList<CustomerWithLocationData> customers) {
        this.customers.set(customers);
    }

    public void setViewBy(DashboardController.ViewByTimeFrame viewBy) {
        this.viewBy.set(viewBy);
    }

    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments.set(appointments);
    }
}
