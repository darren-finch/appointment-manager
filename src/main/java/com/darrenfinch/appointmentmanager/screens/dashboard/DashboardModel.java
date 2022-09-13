package com.darrenfinch.appointmentmanager.screens.dashboard;

import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.data.misc.TimeFilter;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DashboardModel {
    private final ObjectProperty<ObservableList<CustomerWithLocationData>> customers = new SimpleObjectProperty<>();
    private final ObjectProperty<TimeFilter> timeFilter = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<Appointment>> appointments = new SimpleObjectProperty<>();

    public DashboardModel() {
        customers.set(FXCollections.emptyObservableList());
        timeFilter.set(TimeFilter.WEEK);
        appointments.set(FXCollections.emptyObservableList());
    }

    public ObservableList<CustomerWithLocationData> getCustomers() {
        return customers.get();
    }

    public ObjectProperty<ObservableList<CustomerWithLocationData>> customersProperty() {
        return customers;
    }

    public TimeFilter getTimeFilter() {
        return timeFilter.get();
    }

    public ObjectProperty<TimeFilter> timeFilterProperty() {
        return timeFilter;
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

    public void setTimeFilter(TimeFilter timeFilter) {
        this.timeFilter.set(timeFilter);
    }

    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments.set(appointments);
    }
}
