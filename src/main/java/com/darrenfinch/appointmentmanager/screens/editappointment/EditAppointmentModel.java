package com.darrenfinch.appointmentmanager.screens.editappointment;

import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.data.entities.Contact;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import com.darrenfinch.appointmentmanager.common.data.entities.User;
import com.darrenfinch.appointmentmanager.common.exceptions.InvalidAppointmentTypeException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class EditAppointmentModel {
    /**
     * Provides a wrapper for the parameters needed to validate this model.
     * The model does not have the ability to reach into the database and get these parameters itself because MVC architecture
     * would say that it's a bad idea to allow the model to have access to the database. Instead, the controller should get
     * this data and pass it into the model so the model can do its work.
     */
    public record ValidationParameters(List<Appointment> appointmentsForCustomer, List<Appointment> appointmentsForContact) {
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

    /**
     * Constructs an EditAppointmentModel with a time helper service. Ideally this service would not be necessary for this model,
     * but in this case, the time helper service is needed for accurate business logic.
     */
    public EditAppointmentModel(TimeHelper timeHelper) {
        this.timeHelper = timeHelper;

        id.set("0");
        title.set("");
        description.set("");
        location.set("");
        type.set(Appointment.TYPES.get(0));
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

        selectedStartTimeAmOrPm.set(Constants.AM_OR_PM.get(0));
        selectedStartTimeAmOrPm.addListener(selectedStartTimeChangeListener);

        refreshStartDateTime();

        ChangeListener<String> selectedEndTimeChangeListener = (obs, oldVal, newVal) -> refreshEndDateTime();
        selectedEndTimeHour.set(Constants.getHours().get(10));
        selectedEndTimeHour.addListener(selectedEndTimeChangeListener);

        selectedEndTimeMinute.set(Constants.getMinutes().get(0));
        selectedEndTimeMinute.addListener(selectedEndTimeChangeListener);

        selectedEndTimeAmOrPm.set(Constants.AM_OR_PM.get(0));
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
                                        getSelectedStartTimeAmOrPm().equals(Constants.AM_OR_PM.get(0))
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
                                        getSelectedEndTimeAmOrPm().equals(Constants.AM_OR_PM.get(0))
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


    private void setStartTime(LocalTime startTime) {
        List<String> startTimeIn12HourFormat = to12HrFormat(startTime);
        selectedStartTimeHourProperty().set(startTimeIn12HourFormat.get(0));
        selectedStartTimeMinuteProperty().set(startTimeIn12HourFormat.get(1));
        selectedStartTimeAmOrPmProperty().set(startTimeIn12HourFormat.get(3));
    }

    private void setEndTime(LocalTime endTime) {
        List<String> endTimeIn12HourFormat = to12HrFormat(endTime);
        selectedEndTimeHourProperty().set(endTimeIn12HourFormat.get(0));
        selectedEndTimeMinuteProperty().set(endTimeIn12HourFormat.get(1));
        selectedEndTimeAmOrPmProperty().set(endTimeIn12HourFormat.get(3));
    }

    private List<String> to12HrFormat(LocalTime localTime) {
        int hourIn12HourFormat = localTime.getHour();
        String amOrPm = Constants.AM_OR_PM.get(0);
        if (localTime.getHour() == 0) { // 12 AM
            hourIn12HourFormat += 12;
        } else if (localTime.getHour() == 12) { // 12 PM
            amOrPm = Constants.AM_OR_PM.get(1);
        } else if (localTime.getHour() > 12) { // Over 12 PM up to 11 PM
            hourIn12HourFormat -= 12;
            amOrPm = Constants.AM_OR_PM.get(1);
        }

        return List.of(
                String.format("%02d", hourIn12HourFormat),
                String.format("%02d", localTime.getMinute()),
                String.format("%02d", localTime.getSecond()),
                amOrPm
        );
    }

    /**
     * Gets the id value from the id property.
     */
    public String getId() {
        return id.get();
    }

    /**
     * Gets the id list property
     */
    public StringProperty idProperty() {
        return id;
    }

    /**
     * Gets the title value from the title property.
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Gets the title list property
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
     * Gets the description list property
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
     * Gets the location list property
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
     * Gets the type list property
     */
    public StringProperty typeProperty() {
        return type;
    }

    /**
     * Gets the date value from the date property.
     */
    public LocalDate getDate() {
        return date.get();
    }

    /**
     * Gets the date list property
     */
    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    /**
     * Gets the selected start time hour value from the selected start time hour property.
     */
    public String getSelectedStartTimeHour() {
        return selectedStartTimeHour.get();
    }

    /**
     * Gets the selected start time hour list property
     */
    public StringProperty selectedStartTimeHourProperty() {
        return selectedStartTimeHour;
    }

    /**
     * Gets the selected start time minute value from the selected start time minute property.
     */
    public String getSelectedStartTimeMinute() {
        return selectedStartTimeMinute.get();
    }

    /**
     * Gets the selected start time minute list property
     */
    public StringProperty selectedStartTimeMinuteProperty() {
        return selectedStartTimeMinute;
    }

    /**
     * Gets the selected start time AM or PM value from the selected start time AM or PM property.
     */
    public String getSelectedStartTimeAmOrPm() {
        return selectedStartTimeAmOrPm.get();
    }

    /**
     * Gets the selected start time AM or PM list property
     */
    public StringProperty selectedStartTimeAmOrPmProperty() {
        return selectedStartTimeAmOrPm;
    }

    /**
     * Gets the selected start datetime value from the selected start datetime property.
     */
    public ZonedDateTime getSelectedStartDateTime() {
        return selectedStartDateTime.get();
    }

    /**
     * Gets the selected start datetime list property
     */
    public ObjectProperty<ZonedDateTime> selectedStartDateTimeProperty() {
        return selectedStartDateTime;
    }

    /**
     * Gets the selected end time hour value from the selected end time hour property.
     */
    public String getSelectedEndTimeHour() {
        return selectedEndTimeHour.get();
    }

    /**
     * Gets the selected end time hour list property
     */
    public StringProperty selectedEndTimeHourProperty() {
        return selectedEndTimeHour;
    }

    /**
     * Gets the selected end time minute value from the selected end time minute property.
     */
    public String getSelectedEndTimeMinute() {
        return selectedEndTimeMinute.get();
    }

    /**
     * Gets the selected end time minute list property
     */
    public StringProperty selectedEndTimeMinuteProperty() {
        return selectedEndTimeMinute;
    }

    /**
     * Gets the selected end time AM or PM value from the selected end time AM or PM property.
     */
    public String getSelectedEndTimeAmOrPm() {
        return selectedEndTimeAmOrPm.get();
    }

    /**
     * Gets the selected end time AM or PM list property
     */
    public StringProperty selectedEndTimeAmOrPmProperty() {
        return selectedEndTimeAmOrPm;
    }

    /**
     * Gets the selected end datetime value from the selected end datetime property.
     */
    public ZonedDateTime getSelectedEndDateTime() {
        return selectedEndDateTime.get();
    }

    /**
     * Gets the selected end datetime list property
     */
    public ObjectProperty<ZonedDateTime> selectedEndDateTimeProperty() {
        return selectedEndDateTime;
    }

    /**
     * Gets the all customers value from the all customers property.
     */
    public ObservableList<Customer> getAllCustomers() {
        return allCustomers.get();
    }

    /**
     * Gets the all customers list property
     */
    public ObjectProperty<ObservableList<Customer>> allCustomersProperty() {
        return allCustomers;
    }

    /**
     * Gets the selected customer value from the selected customer property.
     */
    public Customer getSelectedCustomer() {
        return selectedCustomer.get();
    }

    /**
     * Gets the selected customer list property
     */
    public ObjectProperty<Customer> selectedCustomerProperty() {
        return selectedCustomer;
    }

    /**
     * Gets the all users value from the all users property.
     */
    public ObservableList<User> getAllUsers() {
        return allUsers.get();
    }

    /**
     * Gets the all users list property
     */
    public ObjectProperty<ObservableList<User>> allUsersProperty() {
        return allUsers;
    }

    /**
     * Gets the selected user value from the selected user property.
     */
    public User getSelectedUser() {
        return selectedUser.get();
    }

    /**
     * Gets the selected user list property
     */
    public ObjectProperty<User> selectedUserProperty() {
        return selectedUser;
    }

    /**
     * Gets the selected contact value from the selected contact property.
     */
    public Contact getSelectedContact() {
        return selectedContact.get();
    }

    /**
     * Gets the selected contact list property
     */
    public ObjectProperty<Contact> selectedContactProperty() {
        return selectedContact;
    }

    /**
     * Gets the all contacts value from the all contacts property.
     */
    public ObservableList<Contact> getAllContacts() {
        return allContacts.get();
    }

    /**
     * Gets the all contacts list property
     */
    public ObjectProperty<ObservableList<Contact>> allContactsProperty() {
        return allContacts;
    }

    /**
     * Gets the error value from the error property.
     */
    public String getError() {
        return error.get();
    }

    /**
     * Gets the error list property
     */
    public StringProperty errorProperty() {
        return error;
    }

    /**
     * Gets all the reasons why the appointment validation was not successful.
     */
    public List<String> getInvalidReasons() {
        return invalidReasons;
    }

    /**
     * Sets the id property.
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     * Sets the title property.
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Sets the description property.
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Sets the location property.
     */
    public void setLocation(String location) {
        this.location.set(location);
    }

    /**
     * Sets the type property.
     */
    public void setType(String type) {
        this.type.set(type);
    }

    /**
     * Sets the date property.
     */
    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    /**
     * Sets the selected start time hour property.
     */
    public void setSelectedStartTimeHour(String selectedStartTimeHour) {
        this.selectedStartTimeHour.set(selectedStartTimeHour);
    }

    /**
     * Sets the selected start time minute property.
     */
    public void setSelectedStartTimeMinute(String selectedStartTimeMinute) {
        this.selectedStartTimeMinute.set(selectedStartTimeMinute);
    }

    /**
     * Sets the selected start time AM or PM property.
     */
    public void setSelectedStartTimeAmOrPm(String selectedStartTimeAmOrPm) {
        this.selectedStartTimeAmOrPm.set(selectedStartTimeAmOrPm);
    }

    /**
     * Sets the selected end time hour property.
     */
    public void setSelectedEndTimeHour(String selectedEndTimeHour) {
        this.selectedEndTimeHour.set(selectedEndTimeHour);
    }

    /**
     * Sets the selected end time minute property.
     */
    public void setSelectedEndTimeMinute(String selectedEndTimeMinute) {
        this.selectedEndTimeMinute.set(selectedEndTimeMinute);
    }

    /**
     * Sets the selected end time AM or PM property.
     */
    public void setSelectedEndTimeAmOrPm(String selectedEndTimeAmOrPm) {
        this.selectedEndTimeAmOrPm.set(selectedEndTimeAmOrPm);
    }

    /**
     * Sets the all customers property.
     */
    public void setAllCustomers(ObservableList<Customer> allCustomers) {
        this.allCustomers.set(allCustomers);
    }

    /**
     * Sets the selected customer property.
     */
    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer.set(selectedCustomer);
    }

    /**
     * Sets the all users property.
     */
    public void setAllUsers(ObservableList<User> allUsers) {
        this.allUsers.set(allUsers);
    }

    /**
     * Sets the selected user property.
     */
    public void setSelectedUser(User selectedUser) {
        this.selectedUser.set(selectedUser);
    }

    /**
     * Sets the all contacts property.
     */
    public void setAllContacts(ObservableList<Contact> allContacts) {
        this.allContacts.set(allContacts);
    }

    /**
     * Sets the selected contact property.
     */
    public void setSelectedContact(Contact selectedContact) {
        this.selectedContact.set(selectedContact);
    }

    /**
     * Sets the error property.
     */
    public void setError(String error) {
        this.error.set(error);
    }

    /**
     * Initializes this model with data from an initial appointment.
     *
     * NOTE: This data model is not fully initialized until the all customer, all contacts, and all users lists have been set separately.
     */
    public void initializeWithAppointment(Appointment appointment) throws InvalidAppointmentTypeException {
        idProperty().set(String.valueOf(appointment.getId()));
        titleProperty().set(appointment.getTitle());
        descriptionProperty().set(appointment.getDescription());
        locationProperty().set(appointment.getLocation());
        dateProperty().set(appointment.getStartDateTime().toLocalDate());
        setStartTime(appointment.getStartDateTime().toLocalTime());
        setEndTime(appointment.getEndDateTime().toLocalTime());
        selectedCustomerProperty().set(getAllCustomers().filtered(customer -> customer.getId() == appointment.getCustomerId()).get(0));
        selectedUserProperty().set(getAllUsers().filtered(user -> user.getId() == appointment.getUserId()).get(0));
        selectedContactProperty().set(getAllContacts().filtered(contact -> contact.getId() == appointment.getContactId()).get(0));

        // This must come last in order to ensure all the other properties are set before throwing an exception (because it returns instantly).
        if (Appointment.TYPES.contains(appointment.getType()))
            typeProperty().set(appointment.getType());
        else
            throw new InvalidAppointmentTypeException(appointment.getId());
    }

    /**
     * Returns the data in the model as an Appointment database entity.
     */
    public Appointment toAppointment() {
        return new Appointment(
                Integer.parseInt(getId()),
                getTitle(),
                getDescription(),
                getLocation(),
                getType(),
                getSelectedContact().getName(),
                getSelectedStartDateTime(),
                getSelectedEndDateTime(),
                getSelectedCustomer().getId(),
                getSelectedUser().getId(),
                getSelectedContact().getId()
        );
    }

    /**
     * Performs a series of checks to ensure that the data in the mode is valid. In order, these checks are:
     * <ol>
     *     <li>No fields are empty</li>
     *     <li>The end datetime is not before the start datetime.</li>
     *     <li>The start and end datetimes do not fall outside of business hours.</li>
     *     <li>The chosen appointment does not overlap any other appointments assigned to the selected customer.</li>
     *     <li>The chosen appointment does not overlap any other appointments assigned to the selected contact.</li>
     * </ol>
     *
     * Once these checks have been performed, it returns a boolean stating whether the check was successful.
     * If the check was not successful, then a list of reasons for why it wasn't successful can be found
     * using the {@link EditAppointmentModel#getInvalidReasons}  getInvalidReasons} method.
     *
     * This method has 2 inline lambdas to help shorten the code and decrease the cognitive load of reading it.
     * One lambda is a forEach lambda used to loop through the appointments assigned to the selected customer.
     * And the other one is exactly the same, only it loops through the appointments assigned to the selected contact.
     */
    public boolean isValid(ValidationParameters validationParameters) {
        invalidReasons.clear();
        return fieldsAreNotEmpty()
                && startIsBeforeEnd()
                && notSchedulingOutsideBusinessHours()
                && notSchedulingOverlappingAppointments(getSelectedCustomer(), validationParameters.appointmentsForCustomer())
                && notSchedulingOverlappingAppointments(getSelectedContact(), validationParameters.appointmentsForContact());
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
                            "Appointment overlaps existing appointment with customer named "
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

    private boolean notSchedulingOverlappingAppointments(Contact contact, List<Appointment> appointmentsForContact) {
        AtomicBoolean ans = new AtomicBoolean(true);
        appointmentsForContact.forEach(appointment -> {
            ZonedDateTime appointmentStart = appointment.getStartDateTime();
            ZonedDateTime appointmentEnd = appointment.getEndDateTime();

            if (appointment.getId() != Integer.parseInt(getId())) {
                if (isAppointmentOverlapping(getSelectedStartDateTime(), getSelectedEndDateTime(), appointmentStart, appointmentEnd)) {
                    ans.set(false);
                    invalidReasons.add(
                            "Appointment overlaps existing appointment with contact named "
                                    + contact.getName()
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
