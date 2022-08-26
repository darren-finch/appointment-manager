package com.darrenfinch.appointmentmanager.common.data.entities;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Appointment {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private final IntegerProperty customerId = new SimpleIntegerProperty();
    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final IntegerProperty contactId = new SimpleIntegerProperty();

    public Appointment(int id, String title, String description, String location, String type, LocalDate startDate, LocalDate endDate, int customerId, int userId, int contactId) {
        this.id.set(id);
        this.description.set(description);
        this.location.set(location);
        this.type.set(type);
        this.startDate.set(startDate);
        this.endDate.set(endDate);
        this.customerId.set(customerId);
        this.userId.set(userId);
        this.contactId.set(contactId);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public int getContactId() {
        return contactId.get();
    }

    public IntegerProperty contactIdProperty() {
        return contactId;
    }
}
