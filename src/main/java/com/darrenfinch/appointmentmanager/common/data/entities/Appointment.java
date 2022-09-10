package com.darrenfinch.appointmentmanager.common.data.entities;

import javafx.beans.property.*;

import java.time.ZonedDateTime;

/**
 * The main class that this application centers around. The company that this app is built for needs some way to track
 * appointments with their customers so they built this app.
 *
 * An Appointment has some meta data, as well as the start/end dates, then the IDs for the customer who we will meet with,
 * the user that created the appointment, and the contact that will meet the customer.
 */
public class Appointment {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final ObjectProperty<ZonedDateTime> startDateTime = new SimpleObjectProperty<>();
    private final ObjectProperty<ZonedDateTime> endDateTime = new SimpleObjectProperty<>();
    private final IntegerProperty customerId = new SimpleIntegerProperty();
    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final IntegerProperty contactId = new SimpleIntegerProperty();

    public Appointment(int id, String title, String description, String location, String type, ZonedDateTime startDateTime, ZonedDateTime endDateTime, int customerId, int userId, int contactId) {
        this.id.set(id);
        this.title.set(title);
        this.description.set(description);
        this.location.set(location);
        this.type.set(type);
        this.startDateTime.set(startDateTime);
        this.endDateTime.set(endDateTime);
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

    public ZonedDateTime getStartDateTime() {
        return startDateTime.get();
    }

    public ObjectProperty<ZonedDateTime> startDateTimeProperty() {
        return startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime.get();
    }

    public ObjectProperty<ZonedDateTime> endDateTimeProperty() {
        return endDateTime;
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

    public void setId(int id) {
        this.id.set(id);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime.set(startDateTime);
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime.set(endDateTime);
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public void setContactId(int contactId) {
        this.contactId.set(contactId);
    }
}
