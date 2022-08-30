package com.darrenfinch.appointmentmanager.screens.editcustomer;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Country;
import com.darrenfinch.appointmentmanager.common.data.entities.FirstLevelDivision;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

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

    // TODO: DETERMINE WHETHER THIS GOES IN CONTROLLER OR MODEL
    public EditCustomerModel(MainRepository mainRepository) {
        id.set("0");
        name.set("");
        phoneNumber.set("");
        address.set("");
        postalCode.set("");

        allCountries.set(mainRepository.getAllCountries());
        country.set(allCountries.get().get(0));

        allFirstLevelDivisionsForCountry.set(mainRepository.getFirstLevelDivisionsForCountry(country.get()));
        firstLevelDivision.set(allFirstLevelDivisionsForCountry.get().get(0));

        country.addListener((obs, oldVal, newVal) -> {
            allFirstLevelDivisionsForCountry.set(mainRepository.getFirstLevelDivisionsForCountry(country.get()));
            firstLevelDivision.set(allFirstLevelDivisionsForCountry.get().get(0));
        });

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
}
