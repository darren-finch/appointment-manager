package com.darrenfinch.appointmentmanager.common.data.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * One of the company's consulting clients. You can add/edit/delete customers as well as schedule appointments with them.
 */
public class Customer {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty postalCode = new SimpleStringProperty();
    private final StringProperty phoneNumber = new SimpleStringProperty();
    private final IntegerProperty divisionId = new SimpleIntegerProperty();

    /**
     * Constructs a Customer with some starting values for its properties.
     * The id can be anything, since the database auto-generates it. It is just used to keep track of the entity in the application.
     */
    public Customer(int id, String name, String address, String postalCode, String phoneNumber, int divisionId) {
        this.id.set(id);
        this.name.set(name);
        this.address.set(address);
        this.postalCode.set(postalCode);
        this.phoneNumber.set(phoneNumber);
        this.divisionId.set(divisionId);
    }

    /**
     * Gets the id value from the id property.
     */
    public int getId() {
        return id.get();
    }

    /**
     * Gets the id property.
     */
    public IntegerProperty idProperty() {
        return id;
    }

    /**
     * Gets the name value from the name property.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Gets the name property.
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Gets the address value from the address property.
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * Gets the address property.
     */
    public StringProperty addressProperty() {
        return address;
    }

    /**
     * Gets the postal code value from the postal code property.
     */
    public String getPostalCode() {
        return postalCode.get();
    }

    /**
     * Gets the postal code property.
     */
    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    /**
     * Gets the phone number value from the phone number property.
     */
    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    /**
     * Gets the phone number property.
     */
    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    /**
     * Gets the division id value from the division id property.
     */
    public int getDivisionId() {
        return divisionId.get();
    }

    /**
     * Gets the division id property.
     */
    public IntegerProperty divisionIdProperty() {
        return divisionId;
    }
}