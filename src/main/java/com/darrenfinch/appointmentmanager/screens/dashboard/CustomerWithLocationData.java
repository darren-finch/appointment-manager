package com.darrenfinch.appointmentmanager.screens.dashboard;

import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CustomerWithLocationData {

    private final Customer customer;
    private final StringProperty divisionName = new SimpleStringProperty();
    private final StringProperty countryName = new SimpleStringProperty();

    public CustomerWithLocationData(Customer customer, String divisionName, String countryName) {
        this.customer = customer;
        this.divisionName.set(divisionName);
        this.countryName.set(countryName);
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getId() {
        return customer.getId();
    }

    public IntegerProperty idProperty() {
        return customer.idProperty();
    }

    public String getName() {
        return customer.getName();
    }

    public StringProperty nameProperty() {
        return customer.nameProperty();
    }

    public String getFullAddress() {
        return customer.getAddress() + ", " + divisionName.get() + ", " + countryName.get();
    }

    public String getAddress() {
        return customer.getAddress();
    }

    public StringProperty addressProperty() {
        return customer.addressProperty();
    }

    public String getPostalCode() {
        return customer.getPostalCode();
    }

    public StringProperty postalCodeProperty() {
        return customer.postalCodeProperty();
    }

    public String getPhoneNumber() {
        return customer.getPhoneNumber();
    }

    public StringProperty phoneNumberProperty() {
        return customer.phoneNumberProperty();
    }

    public int getDivisionId() {
        return customer.getDivisionId();
    }

    public IntegerProperty divisionIdProperty() {
        return customer.divisionIdProperty();
    }

    public String getDivisionName() {
        return divisionName.get();
    }

    public StringProperty divisionNameProperty() {
        return divisionName;
    }

    public String getCountryName() {
        return countryName.get();
    }

    public StringProperty countryNameProperty() {
        return countryName;
    }
}
