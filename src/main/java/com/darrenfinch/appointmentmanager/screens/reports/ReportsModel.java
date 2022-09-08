package com.darrenfinch.appointmentmanager.screens.reports;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ReportsModel {
    private ObjectProperty<ObservableList<NumberOfCustomerAppointmentsForTypeAndMonth>> numberOfCustomerAppointmentsByTypeAndMonth = new SimpleObjectProperty<>();
    private ObjectProperty<ObservableList<ContactSchedule>> contactSchedules = new SimpleObjectProperty<>();
    private ObjectProperty<ObservableList<NumberOfCustomerAppointmentsForContact>> numberOfCustomerAppointmentsByContact = new SimpleObjectProperty<>();

    public ReportsModel() {
        numberOfCustomerAppointmentsByTypeAndMonth.set(FXCollections.observableList(new ArrayList<>()));
        contactSchedules.set(FXCollections.observableList(new ArrayList<>()));
        numberOfCustomerAppointmentsByContact.set(FXCollections.observableList(new ArrayList<>()));
    }

    public ObservableList<NumberOfCustomerAppointmentsForTypeAndMonth> getNumberOfCustomerAppointmentsByTypeAndMonth() {
        return numberOfCustomerAppointmentsByTypeAndMonth.get();
    }

    public ObjectProperty<ObservableList<NumberOfCustomerAppointmentsForTypeAndMonth>> numberOfCustomerAppointmentsByTypeAndMonthProperty() {
        return numberOfCustomerAppointmentsByTypeAndMonth;
    }

    public ObservableList<ContactSchedule> getContactSchedules() {
        return contactSchedules.get();
    }

    public ObjectProperty<ObservableList<ContactSchedule>> contactSchedulesProperty() {
        return contactSchedules;
    }

    public ObservableList<NumberOfCustomerAppointmentsForContact> getNumberOfCustomerAppointmentsByContact() {
        return numberOfCustomerAppointmentsByContact.get();
    }

    public ObjectProperty<ObservableList<NumberOfCustomerAppointmentsForContact>> numberOfCustomerAppointmentsByContactProperty() {
        return numberOfCustomerAppointmentsByContact;
    }

    public void setNumberOfCustomerAppointmentsByTypeAndMonth(ObservableList<NumberOfCustomerAppointmentsForTypeAndMonth> numberOfCustomerAppointmentsByTypeAndMonth) {
        this.numberOfCustomerAppointmentsByTypeAndMonth.set(numberOfCustomerAppointmentsByTypeAndMonth);
    }

    public void setContactSchedules(ObservableList<ContactSchedule> contactSchedules) {
        this.contactSchedules.set(contactSchedules);
    }

    public void setNumberOfCustomerAppointmentsByContact(ObservableList<NumberOfCustomerAppointmentsForContact> customersByLocation) {
        this.numberOfCustomerAppointmentsByContact.set(customersByLocation);
    }
}
