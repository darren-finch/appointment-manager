package com.darrenfinch.appointmentmanager.screens.reports;

import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.data.entities.Contact;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import com.darrenfinch.appointmentmanager.common.data.misc.TimeFilter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ReportsModel {
    private final SimpleStringProperty report1TypeFilter = new SimpleStringProperty();
    private final SimpleStringProperty report2MonthFilter = new SimpleStringProperty();
    private final ObjectProperty<Contact> report3ContactFilter = new SimpleObjectProperty<>();
    private final ObjectProperty<Contact> report4ContactFilter = new SimpleObjectProperty<>();
    private final ObjectProperty<TimeFilter> report4TimeFilter = new SimpleObjectProperty<>();

    private final ObjectProperty<ObservableList<Contact>> allContacts = new SimpleObjectProperty<>();

    private final ObjectProperty<ObservableList<NumberOfAppointmentsForCustomer>> report1Data = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<NumberOfAppointmentsForCustomer>> report2Data = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<ContactAppointment>> report3Data = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<NumberOfAppointmentsForContact>> report4Data = new SimpleObjectProperty<>();

    public ReportsModel() {
        report1TypeFilter.set(Appointment.TYPES.get(0));
        report2MonthFilter.set(Constants.MONTHS.get(0));
        report4TimeFilter.set(TimeFilter.WEEK);
        report1Data.set(FXCollections.observableList(new ArrayList<>()));
        report2Data.set(FXCollections.observableList(new ArrayList<>()));
        report3Data.set(FXCollections.observableList(new ArrayList<>()));
        report4Data.set(FXCollections.observableList(new ArrayList<>()));
    }

    public void initialize(ObservableList<Contact> allContacts) {
        this.allContacts.set(allContacts);
        report3ContactFilter.set(allContacts.get(0));
        report4ContactFilter.set(allContacts.get(0));
    }

    public String getReport1TypeFilter() {
        return report1TypeFilter.get();
    }

    public SimpleStringProperty report1TypeFilterProperty() {
        return report1TypeFilter;
    }

    public String getReport2MonthFilter() {
        return report2MonthFilter.get();
    }

    public SimpleStringProperty report2MonthFilterProperty() {
        return report2MonthFilter;
    }

    public Contact getReport3ContactFilter() {
        return report3ContactFilter.get();
    }

    public ObjectProperty<Contact> report3ContactFilterProperty() {
        return report3ContactFilter;
    }

    public Contact getReport4ContactFilter() {
        return report4ContactFilter.get();
    }

    public ObjectProperty<Contact> report4ContactFilterProperty() {
        return report4ContactFilter;
    }

    public TimeFilter getReport4TimeFilter() {
        return report4TimeFilter.get();
    }

    public ObjectProperty<TimeFilter> report4TimeFilterProperty() {
        return report4TimeFilter;
    }

    public ObservableList<Contact> getAllContacts() {
        return allContacts.get();
    }

    public ObjectProperty<ObservableList<Contact>> allContactsProperty() {
        return allContacts;
    }

    public ObservableList<NumberOfAppointmentsForCustomer> getReport1Data() {
        return report1Data.get();
    }

    public ObjectProperty<ObservableList<NumberOfAppointmentsForCustomer>> report1DataProperty() {
        return report1Data;
    }

    public ObservableList<NumberOfAppointmentsForCustomer> getReport2Data() {
        return report2Data.get();
    }

    public ObjectProperty<ObservableList<NumberOfAppointmentsForCustomer>> report2DataProperty() {
        return report2Data;
    }

    public ObservableList<ContactAppointment> getReport3Data() {
        return report3Data.get();
    }

    public ObjectProperty<ObservableList<ContactAppointment>> report3DataProperty() {
        return report3Data;
    }

    public ObservableList<NumberOfAppointmentsForContact> getReport4Data() {
        return report4Data.get();
    }

    public ObjectProperty<ObservableList<NumberOfAppointmentsForContact>> report4DataProperty() {
        return report4Data;
    }

    public void setReport1TypeFilter(String report1TypeFilter) {
        this.report1TypeFilter.set(report1TypeFilter);
    }

    public void setReport2MonthFilter(String report2MonthFilter) {
        this.report2MonthFilter.set(report2MonthFilter);
    }

    public void setReport3ContactFilter(Contact report3ContactFilter) {
        this.report3ContactFilter.set(report3ContactFilter);
    }

    public void setReport4ContactFilter(Contact report4ContactFilter) {
        this.report4ContactFilter.set(report4ContactFilter);
    }

    public void setReport4TimeFilter(TimeFilter report4TimeFilter) {
        this.report4TimeFilter.set(report4TimeFilter);
    }

    public void setAllContacts(ObservableList<Contact> allContacts) {
        this.allContacts.set(allContacts);
    }

    public void setReport1Data(ObservableList<NumberOfAppointmentsForCustomer> report1Data) {
        this.report1Data.set(report1Data);
    }

    public void setReport2Data(ObservableList<NumberOfAppointmentsForCustomer> report2Data) {
        this.report2Data.set(report2Data);
    }

    public void setReport3Data(ObservableList<ContactAppointment> report3Data) {
        this.report3Data.set(report3Data);
    }

    public void setReport4Data(ObservableList<NumberOfAppointmentsForContact> report4Data) {
        this.report4Data.set(report4Data);
    }
}
