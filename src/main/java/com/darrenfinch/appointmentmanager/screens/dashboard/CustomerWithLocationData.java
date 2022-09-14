package com.darrenfinch.appointmentmanager.screens.dashboard;

import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class was created to allow TableViews to correctly display the full address of a customer.
 */
public class CustomerWithLocationData {

    private final Customer customer;
    private final StringProperty divisionName = new SimpleStringProperty();
    private final StringProperty countryName = new SimpleStringProperty();

    /**
     * Constructs a new customer with location data.
     */
    public CustomerWithLocationData(Customer customer, String divisionName, String countryName) {
        this.customer = customer;
        this.divisionName.set(divisionName);
        this.countryName.set(countryName);
    }

    /**
     * Gets the customer represented by this object
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets the id value from the customer object.
     */
    public int getId() {
        return customer.getId();
    }

    /**
     * Gets the id property from the customer object.
     */
    public IntegerProperty idProperty() {
        return customer.idProperty();
    }


    /**
     * Gets the name value from the customer object.
     */
    public String getName() {
        return customer.getName();
    }

    /**
     * Gets the name property from the customer object.
     */
    public StringProperty nameProperty() {
        return customer.nameProperty();
    }

    /**
     * Gets the full address including division name, country name, and postal code.
     */
    public String getFullAddress() {
        return customer.getAddress() + ", " + divisionName.get() + ", " + countryName.get();
        // TODO: Implement this below and remove postal code column from tableview
        // return customer.getAddress() + ", " + divisionName.get() + " " + postalCodeProperty().get() + ", " + countryName.get();
    }

    /**
     * Gets the address value from the customer object.
     */
    public String getAddress() {
        return customer.getAddress();
    }

    /**
     * Gets the address property from the customer object.
     */
    public StringProperty addressProperty() {
        return customer.addressProperty();
    }

    /**
     * Gets the postal code value from the customer object.
     */
    public String getPostalCode() {
        return customer.getPostalCode();
    }

    /**
     * Gets the postal code property from the customer object.
     */
    public StringProperty postalCodeProperty() {
        return customer.postalCodeProperty();
    }

    /**
     * Gets the phone number value from the customer object.
     */
    public String getPhoneNumber() {
        return customer.getPhoneNumber();
    }

    /**
     * Gets the phone number property from the customer object.
     */
    public StringProperty phoneNumberProperty() {
        return customer.phoneNumberProperty();
    }

    /**
     * Gets the division id value from the customer object.
     */
    public int getDivisionId() {
        return customer.getDivisionId();
    }

    /**
     * Gets the division id property from the customer object.
     */
    public IntegerProperty divisionIdProperty() {
        return customer.divisionIdProperty();
    }

    /**
     * Gets the division name value from the division name property.
     */
    public String getDivisionName() {
        return divisionName.get();
    }

    /**
     * Gets the division name property.
     */
    public StringProperty divisionNameProperty() {
        return divisionName;
    }

    /**
     * Gets the country name value from the country name property.
     */
    public String getCountryName() {
        return countryName.get();
    }

    /**
     * Gets the country name property.
     */
    public StringProperty countryNameProperty() {
        return countryName;
    }
}
