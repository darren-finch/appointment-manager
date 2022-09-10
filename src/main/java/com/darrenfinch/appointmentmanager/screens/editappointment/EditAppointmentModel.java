package com.darrenfinch.appointmentmanager.screens.editappointment;

import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.data.entities.Contact;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import com.darrenfinch.appointmentmanager.common.data.entities.User;
import com.darrenfinch.appointmentmanager.common.services.TimeHelper;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.zone.ZoneRules;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class EditAppointmentModel {
    // This class exists because the validation for this model requires some data from the database, which the controller will be responsible for getting.
    public record ValidationParameters(Customer customer, List<Appointment> appointmentsForCustomer) {
    }

    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final StringProperty selectedStartTimeHour = new SimpleStringProperty();
    private final StringProperty selectedStartTimeMinute = new SimpleStringProperty();
    private final StringProperty selectedStartTimeAmOrPm = new SimpleStringProperty();
    private final ObjectProperty<ZonedDateTime> selectedStartDateTime = new SimpleObjectProperty<>();
    private final StringProperty selectedEndTimeHour = new SimpleStringProperty();
    private final StringProperty selectedEndTimeMinute = new SimpleStringProperty();
    private final StringProperty selectedEndTimeAmOrPm = new SimpleStringProperty();
    private final ObjectProperty<ZonedDateTime> selectedEndDateTime = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<Customer>> allCustomers = new SimpleObjectProperty<>();
    private final ObjectProperty<Customer> selectedCustomer = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<User>> allUsers = new SimpleObjectProperty<>();
    private final ObjectProperty<User> selectedUser = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<Contact>> allContacts = new SimpleObjectProperty<>();
    private final ObjectProperty<Contact> selectedContact = new SimpleObjectProperty<>();
    private final StringProperty error = new SimpleStringProperty();

    private final List<String> invalidReasons = new ArrayList<>();

    private final TimeHelper timeHelper;

    public EditAppointmentModel(TimeHelper timeHelper) {
        this.timeHelper = timeHelper;

        id.set("0");
        title.set("");
        description.set("");
        location.set("");
        type.set("");
        date.set(timeHelper.systemTimeNow().toLocalDate());
        date.addListener((obs, oldVal, newVal) -> {
            refreshStartDateTime();
            refreshEndDateTime();
        });

        ChangeListener<String> selectedStartTimeChangeListener = (obs, oldVal, newVal) -> refreshStartDateTime();
        selectedStartTimeHour.set(Constants.getHours().get(9));
        selectedStartTimeHour.addListener(selectedStartTimeChangeListener);

        selectedStartTimeMinute.set(Constants.getMinutes().get(0));
        selectedStartTimeMinute.addListener(selectedStartTimeChangeListener);

        selectedStartTimeAmOrPm.set(Constants.amOrPm.get(0));
        selectedStartTimeAmOrPm.addListener(selectedStartTimeChangeListener);

        refreshStartDateTime();

        ChangeListener<String> selectedEndTimeChangeListener = (obs, oldVal, newVal) -> refreshEndDateTime();
        selectedEndTimeHour.set(Constants.getHours().get(10));
        selectedEndTimeHour.addListener(selectedEndTimeChangeListener);

        selectedEndTimeMinute.set(Constants.getMinutes().get(0));
        selectedEndTimeMinute.addListener(selectedEndTimeChangeListener);

        selectedEndTimeAmOrPm.set(Constants.amOrPm.get(0));
        selectedEndTimeAmOrPm.addListener(selectedEndTimeChangeListener);

        refreshEndDateTime();

        selectedCustomer.set(null);
        selectedUser.set(null);
        selectedContact.set(null);
        error.set("");
    }

    private void refreshStartDateTime() {
        selectedStartDateTime.set(ZonedDateTime.of(
                        LocalDateTime.of(
                                getDate(),
                                from12HrFormat(
                                        Integer.parseInt(getSelectedStartTimeHour()),
                                        Integer.parseInt(getSelectedStartTimeMinute()),
                                        getSelectedStartTimeAmOrPm().equals(Constants.amOrPm.get(0))
                                )
                        ),
                        timeHelper.defaultZone()
                )
        );
    }

    private void refreshEndDateTime() {
        selectedEndDateTime.set(ZonedDateTime.of(
                        LocalDateTime.of(
                                getDate(),
                                from12HrFormat(
                                        Integer.parseInt(getSelectedEndTimeHour()),
                                        Integer.parseInt(getSelectedEndTimeMinute()),
                                        getSelectedEndTimeAmOrPm().equals(Constants.amOrPm.get(0))
                                )
                        ),
                        timeHelper.defaultZone()
                )
        );
    }

    private LocalTime from12HrFormat(int hour, int minute, boolean am) {
        int hourIn24HourFormat = hour;
        if (hour == 12 && am) {
            hourIn24HourFormat -= 12;
        } else if (hour != 12 && !am) {
            hourIn24HourFormat += 12;
        }

        return LocalTime.of(hourIn24HourFormat, minute, 0);
    }

    public void setStartTime(LocalTime startTime) {
        List<String> startTimeIn12HourFormat = to12HrFormat(startTime);
        selectedStartTimeHourProperty().set(startTimeIn12HourFormat.get(0));
        selectedStartTimeMinuteProperty().set(startTimeIn12HourFormat.get(1));
        selectedStartTimeAmOrPmProperty().set(startTimeIn12HourFormat.get(3));
    }

    public void setEndTime(LocalTime endTime) {
        List<String> endTimeIn12HourFormat = to12HrFormat(endTime);
        selectedEndTimeHourProperty().set(endTimeIn12HourFormat.get(0));
        selectedEndTimeMinuteProperty().set(endTimeIn12HourFormat.get(1));
        selectedEndTimeAmOrPmProperty().set(endTimeIn12HourFormat.get(3));
    }

    public List<String> to12HrFormat(LocalTime localTime) {
        int hourIn12HourFormat = localTime.getHour();
        String amOrPm = Constants.amOrPm.get(0);
        if (localTime.getHour() == 0) {
            hourIn12HourFormat += 12;
        } else if (localTime.getHour() > 12) {
            hourIn12HourFormat -= 12;
            amOrPm = Constants.amOrPm.get(1);
        }

        return List.of(
                String.format("%02d", hourIn12HourFormat),
                String.format("%02d", localTime.getMinute()),
                String.format("%02d", localTime.getSecond()),
                amOrPm
        );
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

    public ZonedDateTime getSelectedStartDateTime() {
        return selectedStartDateTime.get();
    }

    public ObjectProperty<ZonedDateTime> selectedStartDateTimeProperty() {
        return selectedStartDateTime;
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

    public ZonedDateTime getSelectedEndDateTime() {
        return selectedEndDateTime.get();
    }

    public ObjectProperty<ZonedDateTime> selectedEndDateTimeProperty() {
        return selectedEndDateTime;
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

    public List<String> getInvalidReasons() {
        return invalidReasons;
    }

    public void setId(String id) {
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

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public void setSelectedStartTimeHour(String selectedStartTimeHour) {
        this.selectedStartTimeHour.set(selectedStartTimeHour);
    }

    public void setSelectedStartTimeMinute(String selectedStartTimeMinute) {
        this.selectedStartTimeMinute.set(selectedStartTimeMinute);
    }

    public void setSelectedStartTimeAmOrPm(String selectedStartTimeAmOrPm) {
        this.selectedStartTimeAmOrPm.set(selectedStartTimeAmOrPm);
    }

    public void setSelectedStartDateTime(ZonedDateTime selectedStartDateTime) {
        this.selectedStartDateTime.set(selectedStartDateTime);
    }

    public void setSelectedEndTimeHour(String selectedEndTimeHour) {
        this.selectedEndTimeHour.set(selectedEndTimeHour);
    }

    public void setSelectedEndTimeMinute(String selectedEndTimeMinute) {
        this.selectedEndTimeMinute.set(selectedEndTimeMinute);
    }

    public void setSelectedEndTimeAmOrPm(String selectedEndTimeAmOrPm) {
        this.selectedEndTimeAmOrPm.set(selectedEndTimeAmOrPm);
    }

    public void setSelectedEndDateTime(ZonedDateTime selectedEndDateTime) {
        this.selectedEndDateTime.set(selectedEndDateTime);
    }

    public void setAllCustomers(ObservableList<Customer> allCustomers) {
        this.allCustomers.set(allCustomers);
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer.set(selectedCustomer);
    }

    public void setAllUsers(ObservableList<User> allUsers) {
        this.allUsers.set(allUsers);
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser.set(selectedUser);
    }

    public void setAllContacts(ObservableList<Contact> allContacts) {
        this.allContacts.set(allContacts);
    }

    public void setSelectedContact(Contact selectedContact) {
        this.selectedContact.set(selectedContact);
    }

    public void setError(String error) {
        this.error.set(error);
    }

    public void initializeWithAppointment(Appointment appointment) {
        idProperty().set(String.valueOf(appointment.getId()));
        titleProperty().set(appointment.getTitle());
        descriptionProperty().set(appointment.getDescription());
        locationProperty().set(appointment.getLocation());
        typeProperty().set(appointment.getType());
        dateProperty().set(appointment.getStartDateTime().toLocalDate());
        setStartTime(appointment.getStartDateTime().toLocalTime());
        setEndTime(appointment.getEndDateTime().toLocalTime());
        selectedCustomerProperty().set(getAllCustomers().filtered(customer -> customer.getId() == appointment.getCustomerId()).get(0));
        selectedUserProperty().set(getAllUsers().filtered(user -> user.getId() == appointment.getUserId()).get(0));
        selectedContactProperty().set(getAllContacts().filtered(contact -> contact.getId() == appointment.getContactId()).get(0));
    }

    public Appointment toAppointment() {
        return new Appointment(
                Integer.parseInt(getId()),
                getTitle(),
                getDescription(),
                getLocation(),
                getType(),
                getSelectedStartDateTime(),
                getSelectedEndDateTime(),
                getSelectedCustomer().getId(),
                getSelectedUser().getId(),
                getSelectedContact().getId()
        );
    }

    public boolean isValid(ValidationParameters validationParameters) {
        invalidReasons.clear();
        return fieldsAreNotEmpty()
                && startIsBeforeEnd()
                && notSchedulingOutsideBusinessHours()
                && notSchedulingOverlappingAppointments(validationParameters.customer, validationParameters.appointmentsForCustomer());
    }

    private boolean fieldsAreNotEmpty() {
        if (getTitle().isEmpty()
        || getDescription().isEmpty()
        || getLocation().isEmpty()
        || getType().isEmpty()) {
            invalidReasons.add("Title, Description, Location, and Type cannot be empty.");
            return false;
        }

        return true;
    }

    private boolean startIsBeforeEnd() {
        if (getSelectedStartDateTime().isBefore(getSelectedEndDateTime())) {
            return true;
        } else {
            invalidReasons.add("Start date must be before end date.");
            return false;
        }
    }

    private boolean notSchedulingOutsideBusinessHours() {
        ZoneId estZoneId = ZoneId.of("America/New_York");

        LocalTime selectedStartTimeInEST = getSelectedStartDateTime().withZoneSameInstant(estZoneId).toLocalTime();
        LocalTime selectedEndTimeInEST = getSelectedEndDateTime().withZoneSameInstant(estZoneId).toLocalTime();

        if (selectedStartTimeInEST.isBefore(Constants.BUSINESS_START_TIME) || selectedEndTimeInEST.isBefore(Constants.BUSINESS_START_TIME)
                || selectedStartTimeInEST.isAfter(Constants.BUSINESS_END_TIME) || selectedEndTimeInEST.isAfter(Constants.BUSINESS_END_TIME)) {

            LocalTime businessStartTimeInLocalTimeZone = ZonedDateTime.of(getDate(), Constants.BUSINESS_START_TIME, estZoneId).withZoneSameInstant(timeHelper.defaultZone()).toLocalTime();
            LocalTime businessEndTimeInLocalTimeZone = ZonedDateTime.of(getDate(), Constants.BUSINESS_END_TIME, estZoneId).withZoneSameInstant(timeHelper.defaultZone()).toLocalTime();

            invalidReasons.add(
                    "Appointment schedule is outside of business hours.\nStandard business hours in your timezone are "
                            + businessStartTimeInLocalTimeZone.format(DateTimeFormatter.ofPattern(Constants.STANDARD_TIME_FORMAT))
                            + " to "
                            + businessEndTimeInLocalTimeZone.format(DateTimeFormatter.ofPattern(Constants.STANDARD_TIME_FORMAT))
            );
            return false;
        } else {
            return true;
        }
    }

    private boolean notSchedulingOverlappingAppointments(Customer customer, List<Appointment> appointmentsForCustomer) {
        AtomicBoolean ans = new AtomicBoolean(true);
        appointmentsForCustomer.forEach(appointment -> {
            ZonedDateTime appointmentStart = appointment.getStartDateTime();
            ZonedDateTime appointmentEnd = appointment.getEndDateTime();

            if (appointment.getId() != Integer.parseInt(getId())) {
                if (isAppointmentOverlapping(getSelectedStartDateTime(), getSelectedEndDateTime(), appointmentStart, appointmentEnd)) {
                    ans.set(false);
                    invalidReasons.add(
                            "Appointment overlaps existing appointment with "
                                    + customer.getName()
                                    + ".\nThe existing appointment is between "
                                    + appointmentStart.format(DateTimeFormatter.ofPattern(Constants.STANDARD_DATE_TIME_FORMAT))
                                    + "\nand "
                                    + appointmentEnd.format(DateTimeFormatter.ofPattern(Constants.STANDARD_DATE_TIME_FORMAT))
                    );
                }
            }
        });

        return ans.get();
    }

    private boolean isAppointmentOverlapping(ZonedDateTime newAppointmentStart, ZonedDateTime newAppointmentEnd, ZonedDateTime appointmentStart, ZonedDateTime appointmentEnd) {
        return !((newAppointmentStart.isBefore(appointmentStart) && !newAppointmentEnd.isAfter(appointmentStart))
                || (!newAppointmentStart.isBefore(appointmentEnd) && newAppointmentEnd.isAfter(appointmentEnd)));
    }
}
