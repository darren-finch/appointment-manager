package com.darrenfinch.appointmentmanager.screens.dashboard;

import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.data.misc.TimeFilter;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Contains all relevant data for the Dashboard screen.
 */
public class DashboardModel {
    private final ObjectProperty<ObservableList<CustomerWithLocationData>> customers = new SimpleObjectProperty<>();
    private final ObjectProperty<TimeFilter> timeFilter = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<Appointment>> appointments = new SimpleObjectProperty<>();

    /**
     * Constructs the model with default values.
     */
    public DashboardModel() {
        customers.set(FXCollections.emptyObservableList());
        timeFilter.set(TimeFilter.WEEK);
        appointments.set(FXCollections.emptyObservableList());
    }

    /**
     * Gets the customers list value from the customers list property.
     */
    public ObservableList<CustomerWithLocationData> getCustomers() {
        return customers.get();
    }

    /**
     * Gets the customers list property
     */
    public ObjectProperty<ObservableList<CustomerWithLocationData>> customersProperty() {
        return customers;
    }

    /**
     * Gets the time filter value from the time filter property.
     */
    public TimeFilter getTimeFilter() {
        return timeFilter.get();
    }

    /**
     * Gets the time filter property
     */
    public ObjectProperty<TimeFilter> timeFilterProperty() {
        return timeFilter;
    }

    /**
     * Gets the appointments list value from the appointments list property.
     */
    public ObservableList<Appointment> getAppointments() {
        return appointments.get();
    }

    /**
     * Gets the appointments list property
     */
    public ObjectProperty<ObservableList<Appointment>> appointmentsProperty() {
        return appointments;
    }

    /**
     * Sets the customers list property.
     */
    public void setCustomers(ObservableList<CustomerWithLocationData> customers) {
        this.customers.set(customers);
    }

    /**
     * Sets the time filter property.
     */
    public void setTimeFilter(TimeFilter timeFilter) {
        this.timeFilter.set(timeFilter);
    }

    /**
     * Sets the appointments list property.
     */
    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments.set(appointments);
    }
}
