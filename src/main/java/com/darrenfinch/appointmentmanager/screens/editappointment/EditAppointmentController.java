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
    private ComboBox<String> typeComboBox;
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
                model.setAllCustomers(customers);
                model.setAllUsers(users);
                model.setAllContacts(contacts);

                model.setSelectedCustomer(customers.get(0));
                model.setSelectedUser(users.get(0));
                model.setSelectedContact(contacts.get(0));

                if (appointment != null) {
                    try {
                        model.initializeWithAppointment(appointment);
                    } catch (InvalidAppointmentTypeException e) {
                        model.setError(e.getMessage());
                    }
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
        loadFormDataTask.setOnFailed(workerStateEvent -> {
            model.setError("An error occurred when loading the appointment data.");
            workerStateEvent.consume();
        });
        executorService.execute(loadFormDataTask);
    }

    private void setupUI() {
        appointmentIdTextField.textProperty().bind(model.idProperty());

        titleTextField.textProperty().bindBidirectional(model.titleProperty());

        descriptionTextArea.textProperty().bindBidirectional(model.descriptionProperty());

        locationTextField.textProperty().bindBidirectional(model.locationProperty());

        typeComboBox.setItems(Appointment.TYPES);
        typeComboBox.valueProperty().bindBidirectional(model.typeProperty());

        datePicker.valueProperty().bindBidirectional(model.dateProperty());

        startTimeHourComboBox.setItems(FXCollections.observableList(Constants.getHours()));
        startTimeMinuteComboBox.setItems(FXCollections.observableList(Constants.getMinutes()));
        startTimeAmOrPmComboBox.setItems(FXCollections.observableList(Constants.AM_OR_PM));
        startTimeHourComboBox.valueProperty().bindBidirectional(model.selectedStartTimeHourProperty());
        startTimeMinuteComboBox.valueProperty().bindBidirectional(model.selectedStartTimeMinuteProperty());
        startTimeAmOrPmComboBox.valueProperty().bindBidirectional(model.selectedStartTimeAmOrPmProperty());

        endTimeHourComboBox.setItems(FXCollections.observableList(Constants.getHours()));
        endTimeMinuteComboBox.setItems(FXCollections.observableList(Constants.getMinutes()));
        endTimeAmOrPmComboBox.setItems(FXCollections.observableList(Constants.AM_OR_PM));
        endTimeHourComboBox.valueProperty().bindBidirectional(model.selectedEndTimeHourProperty());
        endTimeMinuteComboBox.valueProperty().bindBidirectional(model.selectedEndTimeMinuteProperty());
        endTimeAmOrPmComboBox.valueProperty().bindBidirectional(model.selectedEndTimeAmOrPmProperty());

        customerComboBox.setCellFactory(customerListView -> new CustomerListCell());
        customerComboBox.setButtonCell(new CustomerListCell());
        customerComboBox.itemsProperty().bind(model.allCustomersProperty());
        customerComboBox.valueProperty().bindBidirectional(model.selectedCustomerProperty());

        userComboBox.setCellFactory(userListView -> new UserListCell());
        userComboBox.setButtonCell(new UserListCell());
        userComboBox.itemsProperty().bind(model.allUsersProperty());
        userComboBox.valueProperty().bindBidirectional(model.selectedUserProperty());

        contactComboBox.setCellFactory(contactListView -> new ContactListCell());
        contactComboBox.setButtonCell(new ContactListCell());
        contactComboBox.itemsProperty().bind(model.allContactsProperty());
        contactComboBox.valueProperty().bindBidirectional(model.selectedContactProperty());

        errorLabel.textProperty().bind(model.errorProperty());
    }

    public void goBack() {
        dialogManager.showConfirmationDialog("You will lose any unsaved data if you go back. Are you sure?", screenNavigator::goBack, null);
    }

    public void save() {
        if (!loadFormDataTask.isDone())
            return;

        model.setError("Validating...");
        errorLabel.setVisible(true);

        Task<Void> validateModelDataTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                ObservableList<Appointment> appointmentsForCustomer = mainRepository.getAppointmentsForCustomer(model.getSelectedCustomer().getId());
                ObservableList<Appointment> appointmentsForContact = mainRepository.getAppointmentsForContact(model.getSelectedContact().getId());

                Platform.runLater(() -> {
                    if (model.isValid(new EditAppointmentModel.ValidationParameters(appointmentsForCustomer, appointmentsForContact))) {
                        errorLabel.setVisible(false);

                        Appointment appointmentFromModel = model.toAppointment();
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
                    } else {
                        String errors = model.getInvalidReasons().stream().reduce((prev, curr) -> prev + "\n" + curr).get();
                        model.setError(errors);
                    }
                });

                return null;
            }
        };

        executorService.execute(validateModelDataTask);
    }

    private static class CustomerListCell extends ListCell<Customer> {
        @Override
        protected void updateItem(Customer customer, boolean empty) {
            super.updateItem(customer, empty);
            if (!empty && customer != null)
                setText(customer.getName());
            else
                setText("");
        }
    }

    private static class UserListCell extends ListCell<User> {
        @Override
        protected void updateItem(User user, boolean empty) {
            super.updateItem(user, empty);
            if (!empty && user != null)
                setText(user.getName());
            else
                setText("");
        }
    }

    private static class ContactListCell extends ListCell<Contact> {
        @Override
        protected void updateItem(Contact contact, boolean empty) {
            super.updateItem(contact, empty);
            if (!empty && contact != null)
                setText(contact.getName());
            else
                setText("");
        }
    }
}
