package com.darrenfinch.appointmentmanager.common.data.entities;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * An Appointment is a meeting between a contact and a customer.
 */
public class Appointment {

    /**
     * A declaration of the types of Appointments you can make. This allows for filtering appointments by type via
     * a combo box instead of a search box (which is trickier to write).
     */
    public static final ObservableList<String> TYPES = FXCollections.observableList(List.of(
            "Planning Session",
            "Briefing",
            "De-Briefing",
            "Executive",
            "Brainstorming",
            "Report",
            "Review",
            "Miscellaneous"
    ));

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty contactName = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final ObjectProperty<ZonedDateTime> startDateTime = new SimpleObjectProperty<>();
    private final ObjectProperty<ZonedDateTime> endDateTime = new SimpleObjectProperty<>();
    private final IntegerProperty customerId = new SimpleIntegerProperty();
    private final IntegerProperty userId = new SimpleIntegerProperty();
    private final IntegerProperty contactId = new SimpleIntegerProperty();

    /**
     * Constructs an Appointment with some starting values for its properties.
     * The id can be anything, since the database auto-generates it. It is just used to keep track of the entity in the application.
     */
    public Appointment(int id, String title, String description, String location, String type, String contactName, ZonedDateTime startDateTime, ZonedDateTime endDateTime, int customerId, int userId, int contactId) {
        this.id.set(id);
        this.title.set(title);
        this.description.set(description);
        this.location.set(location);
        this.type.set(type);
        this.contactName.set(contactName);
        this.startDateTime.set(startDateTime);
        this.endDateTime.set(endDateTime);
        this.customerId.set(customerId);
        this.userId.set(userId);
        this.contactId.set(contactId);
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
     * Gets the location value from the location property.
     */
    public String getLocation() {
        return location.get();
    }

    /**
     * Gets the location property.
     */
    public StringProperty locationProperty() {
        return location;
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
     * Gets the startDateTime value from the startDateTime property.
     */
    public ZonedDateTime getStartDateTime() {
        return startDateTime.get();
    }

    /**
     * Gets the startDateTime property.
     */
    public ObjectProperty<ZonedDateTime> startDateTimeProperty() {
        return startDateTime;
    }

    /**
     * Gets the endDateTime value from the endDateTime property.
     */
    public ZonedDateTime getEndDateTime() {
        return endDateTime.get();
    }

    /**
     * Gets the endDateTime property.
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

    /**
     * Gets the user id value from the user id property.
     */
    public int getUserId() {
        return userId.get();
    }

    /**
     * Gets the user id property.
     */
    public IntegerProperty userIdProperty() {
        return userId;
    }

    /**
     * Gets the contact id value from the contact id property.
     */
    public int getContactId() {
        return contactId.get();
    }

    /**
     * Gets the contact id property.
     */
    public IntegerProperty contactIdProperty() {
        return contactId;
    }
}
