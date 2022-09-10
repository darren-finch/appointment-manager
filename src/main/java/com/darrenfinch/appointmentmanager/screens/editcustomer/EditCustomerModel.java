package com.darrenfinch.appointmentmanager.screens.editcustomer;

import com.darrenfinch.appointmentmanager.common.data.entities.Country;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import com.darrenfinch.appointmentmanager.common.data.entities.FirstLevelDivision;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EditCustomerModel {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty phoneNumber = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty postalCode = new SimpleStringProperty();
    private final ObjectProperty<ObservableList<Country>> allCountries = new SimpleObjectProperty<>();
    private final ObjectProperty<Country> country = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<FirstLevelDivision>> allFirstLevelDivisionsForCountry = new SimpleObjectProperty<>();
    private final ObjectProperty<FirstLevelDivision> firstLevelDivision = new SimpleObjectProperty<>();
    private final StringProperty error = new SimpleStringProperty();

    private final List<String> invalidReasons = new ArrayList<>();

    public EditCustomerModel() {
        id.set("0");
        name.set("");
        phoneNumber.set("");
        address.set("");
        postalCode.set("");
        error.set("");
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public ObservableList<Country> getAllCountries() {
        return allCountries.get();
    }

    public ObjectProperty<ObservableList<Country>> allCountriesProperty() {
        return allCountries;
    }

    public Country getCountry() {
        return country.get();
    }

    public ObjectProperty<Country> countryProperty() {
        return country;
    }

    public FirstLevelDivision getFirstLevelDivision() {
        return firstLevelDivision.get();
    }

    public ObjectProperty<FirstLevelDivision> firstLevelDivisionProperty() {
        return firstLevelDivision;
    }

    public ObservableList<FirstLevelDivision> getAllFirstLevelDivisionsForCountry() {
        return allFirstLevelDivisionsForCountry.get();
    }

    public ObjectProperty<ObservableList<FirstLevelDivision>> allFirstLevelDivisionsForCountryProperty() {
        return allFirstLevelDivisionsForCountry;
    }

    public String getError() {
        return error.get();
    }

    public StringProperty errorProperty() {
        return error;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public void setAllCountries(ObservableList<Country> allCountries) {
        this.allCountries.set(allCountries);
    }

    public void setCountry(Country country) {
        this.country.set(country);
    }

    public void setAllFirstLevelDivisionsForCountry(ObservableList<FirstLevelDivision> allFirstLevelDivisionsForCountry) {
        this.allFirstLevelDivisionsForCountry.set(allFirstLevelDivisionsForCountry);
    }

    public void setFirstLevelDivision(FirstLevelDivision firstLevelDivision) {
        this.firstLevelDivision.set(firstLevelDivision);
    }

    public void setError(String error) {
        this.error.set(error);
    }

    public void initializeWithCustomer(Customer customer) {
        setId(String.valueOf(customer.getId()));
        setName(customer.getName());
        setPhoneNumber(customer.getPhoneNumber());
        setAddress(customer.getAddress());
        setPostalCode(customer.getPostalCode());
    }

    public Customer toCustomer() {
        return new Customer(
                Integer.parseInt(getId()),
                getName(),
                getAddress(),
                getPostalCode(),
                getPhoneNumber(),
                getFirstLevelDivision().getId()
        );
    }

    public boolean isValid() {
        invalidReasons.clear();
        return fieldsAreNotEmpty();
    }

    private boolean fieldsAreNotEmpty() {
        if (getName().isEmpty()
                || getPhoneNumber().isEmpty()
                || getAddress().isEmpty()
                || getPostalCode().isEmpty()) {
            invalidReasons.add("Name, Phone Number, Address, and Postal Code cannot be empty.");
            return false;
        }

        return true;
    }

    public List<String> getInvalidReasons() {
        return invalidReasons;
    }
}
