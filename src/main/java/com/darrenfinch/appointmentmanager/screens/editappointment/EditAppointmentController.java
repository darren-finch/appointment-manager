package com.darrenfinch.appointmentmanager.screens.editappointment;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.data.entities.Contact;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import com.darrenfinch.appointmentmanager.common.data.entities.User;
import com.darrenfinch.appointmentmanager.common.services.DialogManager;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.common.services.UserManager;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.*;
import java.util.concurrent.ExecutorService;

public class EditAppointmentController {
    @FXML
    private TextField appointmentIdTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextField locationTextField;
    @FXML
    private TextField typeTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> startTimeHourComboBox;
    @FXML
    private ComboBox<String> startTimeMinuteComboBox;
    @FXML
    private ComboBox<String> startTimeAmOrPmComboBox;
    @FXML
    private ComboBox<String> endTimeHourComboBox;
    @FXML
    private ComboBox<String> endTimeMinuteComboBox;
    @FXML
    private ComboBox<String> endTimeAmOrPmComboBox;
    @FXML
    private ComboBox<Customer> customerComboBox;
    @FXML
    private ComboBox<User> userComboBox;
    @FXML
    private ComboBox<Contact> contactComboBox;
    @FXML
    private Label errorLabel;
    private final ScreenNavigator screenNavigator;
    private final DialogManager dialogManager;
    private final UserManager userManager;
    private final MainRepository mainRepository;
    private final ExecutorService executorService;
    private final EditAppointmentModel model;

    private int appointmentId = Constants.INVALID_ID;
    private boolean isEditingExistingAppointment = false;

    private final Task<Void> loadFormDataTask = new Task<>() {
        @Override
        protected Void call() throws Exception {
            ObservableList<Customer> customers = mainRepository.getAllCustomers();
            ObservableList<User> users = mainRepository.getAllUsers();
            ObservableList<Contact> contacts = mainRepository.getAllContacts();
            Appointment appointment = isEditingExistingAppointment ? mainRepository.getAppointment(appointmentId) : null;

            Platform.runLater(() -> {
                model.allCustomersProperty().set(customers);
                model.allUsersProperty().set(users);
                model.allContactsProperty().set(contacts);

                if (appointment != null) {
                    model.idProperty().set(String.valueOf(appointment.getId()));
                    model.titleProperty().set(appointment.getTitle());
                    model.descriptionProperty().set(appointment.getDescription());
                    model.locationProperty().set(appointment.getLocation());
                    model.typeProperty().set(appointment.getType());

                    model.dateProperty().set(appointment.getStartDateTime().toLocalDate());

                    ZonedDateTime startDateTime = appointment.getStartDateTime();
                    int startDateHour = startDateTime.getHour() % 11;
                    String startDateHourString = String.format("%02d", startDateHour);
                    String startDateMinuteString = String.format("%02d", startDateTime.getMinute());
                    model.selectedStartTimeHourProperty().set(startDateHourString);
                    model.selectedStartTimeMinuteProperty().set(startDateMinuteString);
                    model.selectedStartTimeAmOrPmProperty().set(startDateTime.getHour() < 12 ? "AM" : "PM");

                    ZonedDateTime endDateTime = appointment.getEndDateTime();
                    int endDateHour = endDateTime.getHour() % 11;
                    String endDateHourString = String.format("%02d", endDateHour);
                    String endDateMinuteString = String.format("%02d", endDateTime.getMinute());
                    model.selectedEndTimeHourProperty().set(endDateHourString);
                    model.selectedEndTimeMinuteProperty().set(endDateMinuteString);
                    model.selectedEndTimeAmOrPmProperty().set(endDateTime.getHour() < 12 ? "AM" : "PM");

                    model.selectedCustomerProperty().set(model.getAllCustomers().filtered(customer -> customer.getId() == appointment.getCustomerId()).get(0));
                    model.selectedUserProperty().set(model.getAllUsers().filtered(user -> user.getId() == appointment.getUserId()).get(0));
                    model.selectedContactProperty().set(model.getAllContacts().filtered(contact -> contact.getId() == appointment.getContactId()).get(0));
                }
            });

            return null;
        }
    };

    public EditAppointmentController(ScreenNavigator screenNavigator, DialogManager dialogManager, UserManager userManager, MainRepository mainRepository, ExecutorService executorService, EditAppointmentModel model) {
        this.screenNavigator = screenNavigator;
        this.dialogManager = dialogManager;
        this.userManager = userManager;
        this.mainRepository = mainRepository;
        this.executorService = executorService;
        this.model = model;
    }

    @FXML
    public void initialize() {
        setupArguments();
        setupUI();
        setupModelData();
    }

    private void setupArguments() {
        appointmentId = (int) screenNavigator.getArgument("appointmentId");
        isEditingExistingAppointment = appointmentId > Constants.INVALID_ID;
    }

    private void setupModelData() {
        loadFormDataTask.onFailedProperty().set(workerStateEvent -> {
            model.errorProperty().set("An error occurred when loading the appointment data.");
            workerStateEvent.consume();
        });
        executorService.execute(loadFormDataTask);
    }

    private void setupUI() {
        appointmentIdTextField.textProperty().bind(model.idProperty());

        titleTextField.textProperty().bindBidirectional(model.titleProperty());

        descriptionTextArea.textProperty().bindBidirectional(model.descriptionProperty());

        locationTextField.textProperty().bindBidirectional(model.locationProperty());

        typeTextField.textProperty().bindBidirectional(model.typeProperty());

        datePicker.valueProperty().bindBidirectional(model.dateProperty());

        startTimeHourComboBox.itemsProperty().set(FXCollections.observableList(Constants.getHours()));
        startTimeMinuteComboBox.itemsProperty().set(FXCollections.observableList(Constants.getMinutes()));
        startTimeAmOrPmComboBox.itemsProperty().set(FXCollections.observableList(Constants.amOrPm));
        startTimeHourComboBox.valueProperty().bindBidirectional(model.selectedStartTimeHourProperty());
        startTimeMinuteComboBox.valueProperty().bindBidirectional(model.selectedStartTimeMinuteProperty());
        startTimeAmOrPmComboBox.valueProperty().bindBidirectional(model.selectedStartTimeAmOrPmProperty());

        endTimeHourComboBox.itemsProperty().set(FXCollections.observableList(Constants.getHours()));
        endTimeMinuteComboBox.itemsProperty().set(FXCollections.observableList(Constants.getMinutes()));
        endTimeAmOrPmComboBox.itemsProperty().set(FXCollections.observableList(Constants.amOrPm));
        endTimeHourComboBox.valueProperty().bindBidirectional(model.selectedEndTimeHourProperty());
        endTimeMinuteComboBox.valueProperty().bindBidirectional(model.selectedEndTimeMinuteProperty());
        endTimeAmOrPmComboBox.valueProperty().bindBidirectional(model.selectedEndTimeAmOrPmProperty());

        customerComboBox.setCellFactory(customerListView -> new CustomerListCell());
        customerComboBox.setButtonCell(new CustomerListCell());
        customerComboBox.itemsProperty().bind(model.allCustomersProperty());
        customerComboBox.valueProperty().bindBidirectional(model.selectedCustomerProperty());
        customerComboBox.getSelectionModel().selectFirst(); // TODO: Make sure this is thread safe

        userComboBox.setCellFactory(userListView -> new UserListCell());
        userComboBox.setButtonCell(new UserListCell());
        userComboBox.itemsProperty().bind(model.allUsersProperty());
        userComboBox.valueProperty().bindBidirectional(model.selectedUserProperty());
        userComboBox.getSelectionModel().selectFirst();

        contactComboBox.setCellFactory(contactListView -> new ContactListCell());
        contactComboBox.setButtonCell(new ContactListCell());
        contactComboBox.itemsProperty().bind(model.allContactsProperty());
        contactComboBox.valueProperty().bindBidirectional(model.selectedContactProperty());
        contactComboBox.getSelectionModel().selectFirst();

        errorLabel.textProperty().bind(model.errorProperty());
    }

    public void goBack() {
        dialogManager.showConfirmationDialog("You will lose any unsaved data if you go back. Are you sure?", screenNavigator::goBack, null);
    }

    public void save() {
        if (loadFormDataTask.isDone()) {
            Appointment appointmentFromModel = new Appointment(
                    Integer.parseInt(model.getId()),
                    model.getTitle(),
                    model.getDescription(),
                    model.getLocation(),
                    model.getType(),
                    getStartDateTime(),
                    getEndDateTime(),
                    model.getSelectedCustomer().getId(),
                    model.getSelectedUser().getId(),
                    model.getSelectedContact().getId()
            );
            executorService.execute(new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    if (isEditingExistingAppointment) {
                        mainRepository.updateAppointment(appointmentId, appointmentFromModel, userManager.getCurrentUser());
                    } else {
                        mainRepository.addAppointment(appointmentFromModel, userManager.getCurrentUser());
                    }
                    return null;
                }
            });
            screenNavigator.switchToDashboardScreen();
        }
    }

    private ZonedDateTime getStartDateTime() {
        int hour = Integer.parseInt(model.getSelectedStartTimeAmOrPm().equals("AM") ? model.getSelectedStartTimeHour() : model.getSelectedStartTimeHour() + 12) - 1;
        int minute = Integer.parseInt(model.getSelectedStartTimeMinute());
        int second = 0;

        return ZonedDateTime.of(LocalDateTime.of(model.getDate(), LocalTime.of(hour, minute, second)), ZoneId.systemDefault());
    }

    private ZonedDateTime getEndDateTime() {
        int hour = Integer.parseInt(model.getSelectedEndTimeAmOrPm().equals("AM") ? model.getSelectedEndTimeHour() : model.getSelectedEndTimeHour() + 12) - 1;
        int minute = Integer.parseInt(model.getSelectedEndTimeMinute());
        int second = 0;

        return ZonedDateTime.of(LocalDateTime.of(model.getDate(), LocalTime.of(hour, minute, second)), ZoneId.systemDefault());
    }

    // Got these implementations from https://www.baeldung.com/javafx-listview-display-custom-items
    private static class CustomerListCell extends ListCell<Customer> {
        @Override
        protected void updateItem(Customer customer, boolean empty) {
            super.updateItem(customer, empty);
            if (empty || customer == null) {
                setText(null);
            } else {
                setText(customer.getName());
            }
        }
    }

    private static class UserListCell extends ListCell<User> {
        @Override
        protected void updateItem(User user, boolean empty) {
            super.updateItem(user, empty);
            if (empty || user == null) {
                setText(null);
            } else {
                setText(user.getName());
            }
        }
    }

    private static class ContactListCell extends ListCell<Contact> {
        @Override
        protected void updateItem(Contact contact, boolean empty) {
            super.updateItem(contact, empty);
            if (empty || contact == null) {
                setText(null);
            } else {
                setText(contact.getName());
            }
        }
    }
}
