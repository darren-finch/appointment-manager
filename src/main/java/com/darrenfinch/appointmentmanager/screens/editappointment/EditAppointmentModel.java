package com.darrenfinch.appointmentmanager.screens.editappointment;

import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.data.entities.Contact;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import com.darrenfinch.appointmentmanager.common.data.entities.User;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class EditAppointmentModel {
    // This class exists because the validation for this model requires some data from the database, which the controller will be responsible for getting.
    public record ValidationParameters(List<Appointment> appointmentsForCustomer) {
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

    public EditAppointmentModel() {
        id.set("0");
        title.set("");
        description.set("");
        location.set("");
        type.set("");
        date.set(LocalDate.now());
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

        ChangeListener<String> selectedEndTimeChangeListener = (obs, oldVal, newVal) -> refreshEndDateTime();
        selectedEndTimeHour.set(Constants.getHours().get(10));
        selectedEndTimeHour.addListener(selectedEndTimeChangeListener);

        selectedEndTimeMinute.set(Constants.getMinutes().get(0));
        selectedEndTimeMinute.addListener(selectedEndTimeChangeListener);

        selectedEndTimeAmOrPm.set(Constants.amOrPm.get(0));
        selectedEndTimeAmOrPm.addListener(selectedEndTimeChangeListener);

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
                        ZoneId.systemDefault()
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
                        ZoneId.systemDefault()
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
        return startIsBeforeEnd()
                && notSchedulingOutsideBusinessHours()
                && notSchedulingOverlappingAppointments(validationParameters.appointmentsForCustomer());
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
        LocalTime selectedStartTime = getSelectedStartDateTime().toLocalTime();
        LocalTime selectedEndTime = getSelectedEndDateTime().toLocalTime();
        if ((selectedStartTime.equals(Constants.BUSINESS_START_TIME) || (selectedStartTime.isAfter(Constants.BUSINESS_START_TIME) && selectedStartTime.isBefore(Constants.BUSINESS_END_TIME)))
                && ((selectedEndTime.equals(Constants.BUSINESS_END_TIME)) || (selectedEndTime.isAfter(Constants.BUSINESS_START_TIME) && selectedEndTime.isBefore(Constants.BUSINESS_END_TIME)))) {
            return true;
        } else {
            invalidReasons.add("Appointment schedule is outside of business hours.");
            return false;
        }
    }

    private boolean notSchedulingOverlappingAppointments(List<Appointment> appointmentsForCustomer) {
        AtomicBoolean ans = new AtomicBoolean(true);
        List<ZonedDateTime[]> appointmentSchedules = appointmentsForCustomer.stream().map(appointment -> new ZonedDateTime[]{appointment.getStartDateTime(), appointment.getEndDateTime()}).toList();
        appointmentSchedules.forEach(appointmentSchedule -> {
            if (isDateOverlappingAppointmentSchedule(getSelectedStartDateTime(), appointmentSchedule[0], appointmentSchedule[1])
                    || isDateOverlappingAppointmentSchedule(getSelectedEndDateTime(), appointmentSchedule[0], appointmentSchedule[1])) {
                ans.set(false);
                invalidReasons.add(
                        "Appointment overlaps existing appointment between "
                                + appointmentSchedule[0].format(DateTimeFormatter.ofPattern(Constants.STANDARD_DATE_FORMAT))
                                + " and "
                                + appointmentSchedule[1].format(DateTimeFormatter.ofPattern(Constants.STANDARD_DATE_FORMAT))
                );
            }
        });

        return ans.get();
    }

    private boolean isDateOverlappingAppointmentSchedule(ZonedDateTime dateToCheck, ZonedDateTime appointmentStart, ZonedDateTime appointmentEnd) {
        return dateToCheck.isAfter(appointmentStart) && dateToCheck.isBefore(appointmentEnd);
    }
}
