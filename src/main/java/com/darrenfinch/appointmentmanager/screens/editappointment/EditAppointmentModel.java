package com.darrenfinch.appointmentmanager.screens.editappointment;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EditAppointmentModel {
    private final StringProperty idProperty = new SimpleStringProperty();
    private final StringProperty titleProperty = new SimpleStringProperty();
    private final StringProperty descriptionProperty = new SimpleStringProperty();
    private final StringProperty typeProperty = new SimpleStringProperty();
    private final StringProperty contactNameProperty = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> startDateProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> endDateProperty = new SimpleObjectProperty<>();
    private final StringProperty customerIdProperty = new SimpleStringProperty();
    private final StringProperty userIdProperty = new SimpleStringProperty();

    public EditAppointmentModel() {
        idProperty.set("");
        titleProperty.set("");
        descriptionProperty.set("");
        typeProperty.set("");
        contactNameProperty.set("");
        startDateProperty.set(LocalDate.now());
        endDateProperty.set(LocalDate.now().plus(1, ChronoUnit.HOURS));
        customerIdProperty.set("");
        userIdProperty.set("");
    }

    public StringProperty idProperty() {
        return idProperty;
    }

    public StringProperty titleProperty() {
        return titleProperty;
    }

    public StringProperty descriptionProperty() {
        return descriptionProperty;
    }

    public StringProperty typeProperty() {
        return typeProperty;
    }

    public StringProperty contactNameProperty() {
        return contactNameProperty;
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDateProperty;
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return endDateProperty;
    }

    public StringProperty customerIdProperty() {
        return customerIdProperty;
    }

    public StringProperty userIdProperty() {
        return userIdProperty;
    }
}
