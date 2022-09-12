package com.darrenfinch.appointmentmanager.screens.reports;

import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ReportsModel {
    private final SimpleStringProperty appointmentTypeFilter = new SimpleStringProperty();
    private final SimpleStringProperty appointmentMonthFilter1 = new SimpleStringProperty();
    private final SimpleStringProperty appointmentMonthFilter2 = new SimpleStringProperty();
    private final ObjectProperty<ObservableList<NumberOfCustomerAppointmentsForTypeAndMonth>> numberOfCustomerAppointmentsByTypeAndMonth = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<ContactSchedule>> contactSchedules = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<NumberOfContactAppointmentsForMonth>> numberOfContactAppointmentsByMonth = new SimpleObjectProperty<>();

    public ReportsModel() {
        appointmentTypeFilter.set(Appointment.TYPES.get(0));
        appointmentMonthFilter1.set(Constants.MONTHS.get(0));
        appointmentMonthFilter2.set(Constants.MONTHS.get(0));
        numberOfCustomerAppointmentsByTypeAndMonth.set(FXCollections.observableList(new ArrayList<>()));
        contactSchedules.set(FXCollections.observableList(new ArrayList<>()));
        numberOfContactAppointmentsByMonth.set(FXCollections.observableList(new ArrayList<>()));
    }

    public String getAppointmentTypeFilter() {
        return appointmentTypeFilter.get();
    }

    public SimpleStringProperty appointmentTypeFilterProperty() {
        return appointmentTypeFilter;
    }

    public String getAppointmentMonthFilter1() {
        return appointmentMonthFilter1.get();
    }

    public SimpleStringProperty appointmentMonthFilter1Property() {
        return appointmentMonthFilter1;
    }

    public String getAppointmentMonthFilter2() {
        return appointmentMonthFilter2.get();
    }

    public SimpleStringProperty appointmentMonthFilter2Property() {
        return appointmentMonthFilter2;
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

    public ObservableList<NumberOfContactAppointmentsForMonth> getNumberOfContactAppointmentsByMonth() {
        return numberOfContactAppointmentsByMonth.get();
    }

    public ObjectProperty<ObservableList<NumberOfContactAppointmentsForMonth>> numberOfContactAppointmentsByMonthProperty() {
        return numberOfContactAppointmentsByMonth;
    }

    public void setAppointmentTypeFilter(String appointmentTypeFilter) {
        this.appointmentTypeFilter.set(appointmentTypeFilter);
    }

    public void setAppointmentMonthFilter1(String appointmentMonthFilter1) {
        this.appointmentMonthFilter1.set(appointmentMonthFilter1);
    }

    public void setAppointmentMonthFilter2(String appointmentMonthFilter2) {
        this.appointmentMonthFilter2.set(appointmentMonthFilter2);
    }

    public void setNumberOfCustomerAppointmentsByTypeAndMonth(ObservableList<NumberOfCustomerAppointmentsForTypeAndMonth> numberOfCustomerAppointmentsByTypeAndMonth) {
        this.numberOfCustomerAppointmentsByTypeAndMonth.set(numberOfCustomerAppointmentsByTypeAndMonth);
    }

    public void setContactSchedules(ObservableList<ContactSchedule> contactSchedules) {
        this.contactSchedules.set(contactSchedules);
    }

    public void setNumberOfContactAppointmentsByMonth(ObservableList<NumberOfContactAppointmentsForMonth> customersByLocation) {
        this.numberOfContactAppointmentsByMonth.set(customersByLocation);
    }
}
