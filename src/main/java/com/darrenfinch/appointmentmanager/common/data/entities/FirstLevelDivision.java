package com.darrenfinch.appointmentmanager.common.data.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * A top level division of a country. For example, a state in the United States would be a top level division.
 */
public class FirstLevelDivision {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty countryId = new SimpleIntegerProperty();

    /**
     * Constructs a FirstLevelDivision with some starting values for its properties.
     * The id can be anything, since the database pre-populates the countries. It is just used to keep track of the entity in the application.
     */
    public FirstLevelDivision(int id, String name, int countryId) {
        this.id.set(id);
        this.name.set(name);
        this.countryId.set(countryId);
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
     * Gets the country id value from the country id property.
     */
    public int getCountryId() {
        return countryId.get();
    }

    /**
     * Gets the id property.
     */
    public IntegerProperty countryIdProperty() {
        return countryId;
    }
}