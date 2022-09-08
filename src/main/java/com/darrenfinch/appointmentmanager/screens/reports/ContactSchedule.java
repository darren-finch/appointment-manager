package com.darrenfinch.appointmentmanager.screens.reports;

import javafx.beans.property.*;

import java.time.ZonedDateTime;

public class ContactSchedule {
    private final StringProperty contactName = new SimpleStringProperty();
    private final StringProperty contactEmail = new SimpleStringProperty();
    private final IntegerProperty appointmentId = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<ZonedDateTime> startDateTime = new SimpleObjectProperty<>();
    private final ObjectProperty<ZonedDateTime> endDateTime = new SimpleObjectProperty<>();
    private final IntegerProperty customerId = new SimpleIntegerProperty();

    public ContactSchedule(String contactName, String contactEmail, int appointmentId, String title, String type, String description, ZonedDateTime startDateTime, ZonedDateTime endDateTime, int customerId) {
        this.contactName.set(contactName);
        this.contactEmail.set(contactEmail);
        this.appointmentId.set(appointmentId);
        this.title.set(title);
        this.type.set(type);
        this.description.set(description);
        this.startDateTime.set(startDateTime);
        this.endDateTime.set(endDateTime);
        this.customerId.set(customerId);
    }

    public String getContactName() {
        return contactName.get();
    }

    public StringProperty contactNameProperty() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail.get();
    }

    public StringProperty contactEmailProperty() {
        return contactEmail;
    }

    public int getAppointmentId() {
        return appointmentId.get();
    }

    public IntegerProperty appointmentIdProperty() {
        return appointmentId;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
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
}
