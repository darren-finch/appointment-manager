package com.darrenfinch.appointmentmanager.common.data.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FirstLevelDivision {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty countryId = new SimpleIntegerProperty();

    public FirstLevelDivision(int id, String name, int countryId) {
        this.id.set(id);
        this.name.set(name);
        this.countryId.set(countryId);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getCountryId() {
        return countryId.get();
    }

    public IntegerProperty countryIdProperty() {
        return countryId;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setCountryId(int countryId) {
        this.countryId.set(countryId);
    }
}