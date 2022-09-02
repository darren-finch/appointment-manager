package com.darrenfinch.appointmentmanager.screens.dashboard;

import com.darrenfinch.appointmentmanager.common.BaseController;
import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import com.darrenfinch.appointmentmanager.common.services.DialogManager;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.common.services.UserManager;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class DashboardController implements BaseController {
    private final ScreenNavigator screenNavigator;
    private final DialogManager dialogManager;
    private final UserManager userManager;
    private final ExecutorService executorService;
    private final MainRepository mainRepository;
    private final DashboardModel model;
    @FXML
    private TableView<Customer> customersTableView;

    @FXML
    private ComboBox<ViewByTimeFrame> viewByComboBox;

    @FXML
    private TableView<Appointment> appointmentsTableView;

    public DashboardController(ScreenNavigator screenNavigator, DialogManager dialogManager, UserManager userManager, ExecutorService executorService, MainRepository mainRepository, DashboardModel model) {
        this.screenNavigator = screenNavigator;
        this.dialogManager = dialogManager;
        this.userManager = userManager;
        this.executorService = executorService;
        this.mainRepository = mainRepository;
        this.model = model;
    }

    @FXML
    public void initialize() {
        // Fill model
        model.getCustomersProperty().set(mainRepository.getAllCustomers());
        model.getAppointmentsProperty().set(mainRepository.getAppointmentsForUserByTimeFrame(userManager.getCurrentUser().getId(), model.getViewBy()));

        // Initialize combo box
        viewByComboBox.setItems(FXCollections.observableList(List.of(ViewByTimeFrame.values())));
        viewByComboBox.valueProperty().bindBidirectional(model.viewByProperty());

        // Bind viewByProperty of model to re-fetch appointments
        model.viewByProperty().addListener((obs, oldVal, newVal) -> {
            model.getAppointmentsProperty().set(mainRepository.getAppointmentsForUserByTimeFrame(userManager.getCurrentUser().getId(), newVal));
        });

        // Bind items of table views to model properties
        customersTableView.itemsProperty().bind(model.getCustomersProperty());
        appointmentsTableView.itemsProperty().bind(model.getAppointmentsProperty());
    }

    public void deleteCustomer() {
        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            dialogManager.showConfirmationDialog(
                    "Are you sure you want to delete this customer?",
                    () -> {
                        executorService.execute(new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                mainRepository.removeCustomer(selectedCustomer.getId());
                                return null;
                            }
                        });
                    },
                    null
            );
        }
    }

    public void updateCustomer() {
        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            screenNavigator.switchToEditCustomerScreen(selectedCustomer.getId());
        }
    }

    public void addCustomer() {
        screenNavigator.switchToEditCustomerScreen(Constants.INVALID_ID);
    }

    public void deleteAppointment() {
        Appointment selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            dialogManager.showConfirmationDialog(
                    "Are you sure you want to delete this appointment?",
                    () -> {
                        executorService.execute(new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                mainRepository.removeAppointment(selectedAppointment.getId());
                                return null;
                            }
                        });
                    },
                    null
            );
        }
    }

    public void updateAppointment() {
        Appointment selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            screenNavigator.switchToEditAppointmentScreen(selectedAppointment.getId());
        }
    }

    public void addAppointment() {
        screenNavigator.switchToEditAppointmentScreen(Constants.INVALID_ID);
    }

    public enum ViewByTimeFrame {
        WEEK ("Week"),
        MONTH ("Month");

        private final String name;

        ViewByTimeFrame(String s) {
            name = s;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}