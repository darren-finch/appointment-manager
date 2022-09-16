package com.darrenfinch.appointmentmanager.screens.reports;

import javafx.beans.property.*;

import java.time.ZonedDateTime;

public class ContactAppointment {
    private final StringProperty contactName = new SimpleStringProperty();
    private final StringProperty contactEmail = new SimpleStringProperty();
    private final IntegerProperty appointmentId = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<ZonedDateTime> startDateTime = new SimpleObjectProperty<>();
    private final ObjectProperty<ZonedDateTime> endDateTime = new SimpleObjectProperty<>();
    private final IntegerProperty customerId = new SimpleIntegerProperty();

    /**
     * Constructs a ContactAppointment with some starting values for its properties.
     */
    public ContactAppointment(String contactName, String contactEmail, int appointmentId, String title, String type, String description, ZonedDateTime startDateTime, ZonedDateTime endDateTime, int customerId) {
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

    /**
     * Gets the contact name value from the contact name property.
     */
    public String getContactName() {
        return contactName.get();
    }

    /**
     * Gets the contact name property.
     */
    public StringProperty contactNameProperty() {
        return contactName;
    }

    /**
     * Gets the contact email value from the contact email property.
     */
    public String getContactEmail() {
        return contactEmail.get();
    }

    /**
     * Gets the contact email property.
     */
    public StringProperty contactEmailProperty() {
        return contactEmail;
    }

    /**
     * Gets the appointment id value from the appointment id property.
     */
    public int getAppointmentId() {
        return appointmentId.get();
    }

    /**
     * Gets the appointment id property.
     */
    public IntegerProperty appointmentIdProperty() {
        return appointmentId;
    }

    /**
     * Gets the title value from the title property.
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Gets the title property.
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * Gets the type value from the type property.
     */
    public String getType() {
        return type.get();
    }

    /**
     * Gets the type property.
     */
    public StringProperty typeProperty() {
        return type;
    }

    /**
     * Gets the description value from the description property.
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Gets the description property.
     */
    public StringProperty descriptionProperty() {
        return description;
    }

    /**
     * Gets the start datetime value from the start datetime property.
     */
    public ZonedDateTime getStartDateTime() {
        return startDateTime.get();
    }

    /**
     * Gets the start datetime property.
     */
    public ObjectProperty<ZonedDateTime> startDateTimeProperty() {
        return startDateTime;
    }

    /**
     * Gets the end datetime value from the end datetime property.
     */
    public ZonedDateTime getEndDateTime() {
        return endDateTime.get();
    }

    /**
     * Gets the end datetime property.
     */
    public ObjectProperty<ZonedDateTime> endDateTimeProperty() {
        return endDateTime;
    }

    /**
     * Gets the customer id value from the customer id property.
     */
    public int getCustomerId() {
        return customerId.get();
    }

    /**
     * Gets the customer id property.
     */
    public IntegerProperty customerIdProperty() {
        return customerId;
    }
}
