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

public class EditCustomerModel {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty phoneNumber = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty postalCode = new SimpleStringProperty();
    private final ObjectProperty<ObservableList<Country>> allCountries = new SimpleObjectProperty<>();
    private final ObjectProperty<Country> selectedCountry = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<FirstLevelDivision>> allFirstLevelDivisionsForCountry = new SimpleObjectProperty<>();
    private final ObjectProperty<FirstLevelDivision> selectedFirstLevelDivision = new SimpleObjectProperty<>();
    private final StringProperty error = new SimpleStringProperty();

    private final List<String> invalidReasons = new ArrayList<>();

    /**
     * Constructs the model with default values.
     */
    public EditCustomerModel() {
        id.set("0");
        name.set("");
        phoneNumber.set("");
        address.set("");
        postalCode.set("");
        error.set("");
    }

    /**
     * Gets the id value from the id property.
     */
    public String getId() {
        return id.get();
    }

    /**
     * Gets the id property
     */
    public StringProperty idProperty() {
        return id;
    }

    /**
     * Gets the name value from the name property.
     */
    public String getName() {
        return name.get();
    }

    /**
     * Gets the name property
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Gets the phone number value from the phone number property.
     */
    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    /**
     * Gets the phone number property
     */
    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    /**
     * Gets the address value from the address property.
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * Gets the address property
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
     * Gets the postal code property
     */
    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    /**
     * Gets the all countries value from the all countries property.
     */
    public ObservableList<Country> getAllCountries() {
        return allCountries.get();
    }

    /**
     * Gets the all countries property
     */
    public ObjectProperty<ObservableList<Country>> allCountriesProperty() {
        return allCountries;
    }

    /**
     * Gets the selected country value from the selected country property.
     */
    public Country getSelectedCountry() {
        return selectedCountry.get();
    }

    /**
     * Gets the selected country property
     */
    public ObjectProperty<Country> selectedCountryProperty() {
        return selectedCountry;
    }

    /**
     * Gets the selected first level division value from the selected first level division property.
     */
    public FirstLevelDivision getSelectedFirstLevelDivision() {
        return selectedFirstLevelDivision.get();
    }

    /**
     * Gets the selected first level division property
     */
    public ObjectProperty<FirstLevelDivision> selectedFirstLevelDivisionProperty() {
        return selectedFirstLevelDivision;
    }

    /**
     * Gets the all first level divisions value from the all first level divisions property.
     */
    public ObservableList<FirstLevelDivision> getAllFirstLevelDivisionsForCountry() {
        return allFirstLevelDivisionsForCountry.get();
    }

    /**
     * Gets the all first level divisions property
     */
    public ObjectProperty<ObservableList<FirstLevelDivision>> allFirstLevelDivisionsForCountryProperty() {
        return allFirstLevelDivisionsForCountry;
    }

    /**
     * Gets the error value from the error property.
     */
    public String getError() {
        return error.get();
    }

    /**
     * Gets the error property
     */
    public StringProperty errorProperty() {
        return error;
    }

    /**
     * Gets all the reasons why the customer validation was not successful.
     */
    public List<String> getInvalidReasons() {
        return invalidReasons;
    }

    /**
     * Sets the id property.
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     * Sets the name property.
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Sets the phone number property.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    /**
     * Sets the address property.
     */
    public void setAddress(String address) {
        this.address.set(address);
    }

    /**
     * Sets the postal code property.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    /**
     * Sets the all countries property.
     */
    public void setAllCountries(ObservableList<Country> allCountries) {
        this.allCountries.set(allCountries);
    }

    /**
     * Sets the selected country property.
     */
    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry.set(selectedCountry);
    }

    /**
     * Sets the all first level divisions property.
     */
    public void setAllFirstLevelDivisionsForCountry(ObservableList<FirstLevelDivision> allFirstLevelDivisionsForCountry) {
        this.allFirstLevelDivisionsForCountry.set(allFirstLevelDivisionsForCountry);
    }

    /**
     * Sets the selected first level divisions property.
     */
    public void setSelectedFirstLevelDivision(FirstLevelDivision selectedFirstLevelDivision) {
        this.selectedFirstLevelDivision.set(selectedFirstLevelDivision);
    }

    /**
     * Sets the error property.
     */
    public void setError(String error) {
        this.error.set(error);
    }

    /**
     * Initializes this model with data from an initial customer.
     *
     * NOTE: This data model is not fully initialized until the all countries property and the all first level divisions property have been set separately.
     */
    public void initializeWithCustomer(Customer customer) {
        setId(String.valueOf(customer.getId()));
        setName(customer.getName());
        setPhoneNumber(customer.getPhoneNumber());
        setAddress(customer.getAddress());
        setPostalCode(customer.getPostalCode());
    }

    /**
     * Returns the data in the model as a Customer database entity.
     */
    public Customer toCustomer() {
        return new Customer(
                Integer.parseInt(getId()),
                getName(),
                getAddress(),
                getPostalCode(),
                getPhoneNumber(),
                getSelectedFirstLevelDivision().getId()
        );
    }

    /**
     * Checks to ensure no text properties are empty.
     *
     * Once this check has been performed, it returns a boolean stating whether the check was successful.
     * If the check was not successful, then a list of reasons for why it wasn't successful can be found
     * using the {@link EditCustomerModel#getInvalidReasons}  getInvalidReasons} method.
     */
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
}
