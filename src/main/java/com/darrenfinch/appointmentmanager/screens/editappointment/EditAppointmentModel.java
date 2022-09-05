package com.darrenfinch.appointmentmanager.screens.editappointment;

import com.darrenfinch.appointmentmanager.common.data.entities.Contact;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import com.darrenfinch.appointmentmanager.common.data.entities.User;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class EditAppointmentModel {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final StringProperty selectedStartTimeHour = new SimpleStringProperty();
    private final StringProperty selectedStartTimeMinute = new SimpleStringProperty();
    private final StringProperty selectedStartTimeAmOrPm = new SimpleStringProperty();
    private final StringProperty selectedEndTimeHour = new SimpleStringProperty();
    private final StringProperty selectedEndTimeMinute = new SimpleStringProperty();
    private final StringProperty selectedEndTimeAmOrPm = new SimpleStringProperty();
    private final ObjectProperty<ObservableList<Customer>> allCustomers = new SimpleObjectProperty<>();
    private final ObjectProperty<Customer> selectedCustomer = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<User>> allUsers = new SimpleObjectProperty<>();
    private final ObjectProperty<User> selectedUser = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<Contact>> allContacts = new SimpleObjectProperty<>();
    private final ObjectProperty<Contact> selectedContact = new SimpleObjectProperty<>();
    private final StringProperty error = new SimpleStringProperty();

    public EditAppointmentModel() {
        id.set("0");
        title.set("");
        description.set("");
        location.set("");
        type.set("");
        date.set(LocalDate.now());
        selectedStartTimeHour.set(Constants.getHours().get(0));
        selectedStartTimeMinute.set(Constants.getMinutes().get(0));
        selectedStartTimeAmOrPm.set(Constants.amOrPm.get(0));
        selectedEndTimeHour.set(Constants.getHours().get(0));
        selectedEndTimeMinute.set(Constants.getMinutes().get(0));
        selectedEndTimeAmOrPm.set(Constants.amOrPm.get(0));
        selectedCustomer.set(null);
        selectedUser.set(null);
        selectedContact.set(null);
        error.set("");
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
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

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public String getSelectedStartTimeHour() {
        return selectedStartTimeHour.get();
    }

    public StringProperty selectedStartTimeHourProperty() {
        return selectedStartTimeHour;
    }

    public String getSelectedStartTimeMinute() {
        return selectedStartTimeMinute.get();
    }

    public StringProperty selectedStartTimeMinuteProperty() {
        return selectedStartTimeMinute;
    }

    public String getSelectedStartTimeAmOrPm() {
        return selectedStartTimeAmOrPm.get();
    }

    public StringProperty selectedStartTimeAmOrPmProperty() {
        return selectedStartTimeAmOrPm;
    }

    public String getSelectedEndTimeHour() {
        return selectedEndTimeHour.get();
    }

    public StringProperty selectedEndTimeHourProperty() {
        return selectedEndTimeHour;
    }

    public String getSelectedEndTimeMinute() {
        return selectedEndTimeMinute.get();
    }

    public StringProperty selectedEndTimeMinuteProperty() {
        return selectedEndTimeMinute;
    }

    public String getSelectedEndTimeAmOrPm() {
        return selectedEndTimeAmOrPm.get();
    }

    public StringProperty selectedEndTimeAmOrPmProperty() {
        return selectedEndTimeAmOrPm;
    }

    public ObservableList<Customer> getAllCustomers() {
        return allCustomers.get();
    }

    public ObjectProperty<ObservableList<Customer>> allCustomersProperty() {
        return allCustomers;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer.get();
    }

    public ObjectProperty<Customer> selectedCustomerProperty() {
        return selectedCustomer;
    }

    public ObservableList<User> getAllUsers() {
        return allUsers.get();
    }

    public ObjectProperty<ObservableList<User>> allUsersProperty() {
        return allUsers;
    }

    public User getSelectedUser() {
        return selectedUser.get();
    }

    public ObjectProperty<User> selectedUserProperty() {
        return selectedUser;
    }

    public Contact getSelectedContact() {
        return selectedContact.get();
    }

    public ObjectProperty<Contact> selectedContactProperty() {
        return selectedContact;
    }

    public ObservableList<Contact> getAllContacts() {
        return allContacts.get();
    }

    public ObjectProperty<ObservableList<Contact>> allContactsProperty() {
        return allContacts;
    }

    public String getError() {
        return error.get();
    }

    public StringProperty errorProperty() {
        return error;
    }
}
