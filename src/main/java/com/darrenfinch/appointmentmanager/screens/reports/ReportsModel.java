package com.darrenfinch.appointmentmanager.screens.reports;

import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class ReportsModel {
    private final SimpleStringProperty appointmentTypeFilter = new SimpleStringProperty();
    private final SimpleStringProperty appointmentMonthFilter = new SimpleStringProperty();
    private final ObjectProperty<ObservableList<NumberOfCustomerAppointmentsForTypeAndMonth>> numberOfCustomerAppointmentsByTypeAndMonth = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<ContactSchedule>> contactSchedules = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<NumberOfCustomerAppointmentsForContact>> numberOfCustomerAppointmentsByContact = new SimpleObjectProperty<>();

    public ReportsModel() {
        appointmentTypeFilter.set(Appointment.TYPES.get(0));
        appointmentMonthFilter.set(DateFormatSymbols.getInstance().getMonths()[0]);
        numberOfCustomerAppointmentsByTypeAndMonth.set(FXCollections.observableList(new ArrayList<>()));
        contactSchedules.set(FXCollections.observableList(new ArrayList<>()));
        numberOfCustomerAppointmentsByContact.set(FXCollections.observableList(new ArrayList<>()));
    }

    public String getAppointmentTypeFilter() {
        return appointmentTypeFilter.get();
    }

    public SimpleStringProperty appointmentTypeFilterProperty() {
        return appointmentTypeFilter;
    }

    public String getAppointmentMonthFilter() {
        return appointmentMonthFilter.get();
    }

    public SimpleStringProperty appointmentMonthFilterProperty() {
        return appointmentMonthFilter;
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

    public void setAppointmentTypeFilter(String appointmentTypeFilter) {
        this.appointmentTypeFilter.set(appointmentTypeFilter);
    }

    public void setAppointmentMonthFilter(String appointmentMonthFilter) {
        this.appointmentMonthFilter.set(appointmentMonthFilter);
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
