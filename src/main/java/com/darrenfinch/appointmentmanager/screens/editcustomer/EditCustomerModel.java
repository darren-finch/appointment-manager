package com.darrenfinch.appointmentmanager.screens.editcustomer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EditCustomerModel {
    private final StringProperty idProperty = new SimpleStringProperty();
    private final StringProperty nameProperty = new SimpleStringProperty();
    private final StringProperty phoneNumberProperty = new SimpleStringProperty();
    private final StringProperty addressProperty = new SimpleStringProperty();
    private final StringProperty postalCodeProperty = new SimpleStringProperty();
    private final StringProperty countryProperty = new SimpleStringProperty();
    private final StringProperty divisionProperty = new SimpleStringProperty();
    private final StringProperty errorProperty = new SimpleStringProperty();

    public EditCustomerModel() {
        idProperty.set("");
        nameProperty.set("");
        phoneNumberProperty.set("");
        addressProperty.set("");
        postalCodeProperty.set("");
        countryProperty.set("");
        divisionProperty.set("");
        errorProperty.set("");
    }

    public StringProperty idProperty() {
        return idProperty;
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumberProperty;
    }

    public StringProperty addressProperty() {
        return addressProperty;
    }

    public StringProperty postalCodeProperty() {
        return postalCodeProperty;
    }

    public StringProperty countryProperty() {
        return countryProperty;
    }

    public StringProperty divisionProperty() {
        return divisionProperty;
    }

    public StringProperty errorProperty() {
        return errorProperty;
    }
}
