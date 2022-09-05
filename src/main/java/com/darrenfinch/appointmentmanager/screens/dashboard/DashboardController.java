package com.darrenfinch.appointmentmanager.screens.dashboard;

import com.darrenfinch.appointmentmanager.common.BaseController;
import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import com.darrenfinch.appointmentmanager.common.services.DialogManager;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.common.services.UserManager;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;
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

    private GetAllCustomersService getAllCustomersService;
    private GetAppointmentsForUserByTimeFrameService getAppointmentsForUserByTimeFrameService;

    public DashboardController(ScreenNavigator screenNavigator, DialogManager dialogManager, UserManager userManager, ExecutorService executorService, MainRepository mainRepository, DashboardModel model) {
        this.screenNavigator = screenNavigator;
        this.dialogManager = dialogManager;
        this.userManager = userManager;
        this.executorService = executorService;
        this.mainRepository = mainRepository;
        this.model = model;
        this.getAllCustomersService = new GetAllCustomersService(mainRepository);
        this.getAppointmentsForUserByTimeFrameService = new GetAppointmentsForUserByTimeFrameService(mainRepository);
    }

    @FXML
    public void initialize() {
        // Initialize services
        getAllCustomersService.setExecutor(executorService);
        getAppointmentsForUserByTimeFrameService.setExecutor(executorService);
        getAppointmentsForUserByTimeFrameService.setUserId(userManager.getCurrentUser().getId());
        getAppointmentsForUserByTimeFrameService.setViewByTimeFrame(ViewByTimeFrame.WEEK);

        // Fill model
        model.customersProperty().bind(getAllCustomersService.valueProperty());
        model.appointmentsProperty().bind(getAppointmentsForUserByTimeFrameService.valueProperty());

        getAllCustomersService.start();
        getAppointmentsForUserByTimeFrameService.start();

        // Initialize combo box
        viewByComboBox.setItems(FXCollections.observableList(List.of(ViewByTimeFrame.values())));
        viewByComboBox.valueProperty().bindBidirectional(model.viewByProperty());

        // Bind viewByProperty of model to re-fetch appointments
        model.viewByProperty().addListener((obs, oldVal, newVal) -> {
            getAppointmentsForUserByTimeFrameService.setViewByTimeFrame(newVal);
            getAppointmentsForUserByTimeFrameService.restart();
        });

        // Bind items of table views to model properties
        customersTableView.itemsProperty().bind(model.customersProperty());
        appointmentsTableView.itemsProperty().bind(model.appointmentsProperty());

        // Ensure date formatting is correct
        TableColumn<Appointment, String> startDateTimeColumn = (TableColumn<Appointment, String>) appointmentsTableView.getColumns().filtered(col -> col.getText().equals("Start")).get(0);
        startDateTimeColumn.setCellValueFactory(appointmentStringCellDataFeatures -> {
            SimpleStringProperty strProp = new SimpleStringProperty();
            strProp.set(appointmentStringCellDataFeatures.getValue().getStartDateTime().format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm a")));
            return strProp;
        });

        TableColumn<Appointment, String> endDateTimeColumn = (TableColumn<Appointment, String>) appointmentsTableView.getColumns().filtered(col -> col.getText().equals("Start")).get(0);
        endDateTimeColumn.setCellValueFactory(appointmentStringCellDataFeatures -> {
            SimpleStringProperty strProp = new SimpleStringProperty();
            strProp.set(appointmentStringCellDataFeatures.getValue().getEndDateTime().format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm a")));
            return strProp;
        });
    }

    public void deleteCustomer() {
        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            dialogManager.showConfirmationDialog(
                    "Are you sure you want to delete this customer?",
                    () -> {
                        DeleteCustomerTask deleteCustomerTask = new DeleteCustomerTask(mainRepository, selectedCustomer.getId());
                        deleteCustomerTask.setOnSucceeded(workerStateEvent -> {
                            getAllCustomersService.restart();
                        });
                        executorService.execute(deleteCustomerTask);
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
                        DeleteAppointmentTask deleteAppointmentTask = new DeleteAppointmentTask(mainRepository, selectedAppointment.getId());
                        deleteAppointmentTask.setOnSucceeded(workerStateEvent -> {
                            getAppointmentsForUserByTimeFrameService.restart();
                        });
                        executorService.execute(deleteAppointmentTask);
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
        WEEK("Week"),
        MONTH("Month");

        private final String name;

        ViewByTimeFrame(String s) {
            name = s;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class GetAllCustomersService extends Service<ObservableList<Customer>> {
        private final MainRepository mainRepository;

        public GetAllCustomersService(MainRepository mainRepository) {
            this.mainRepository = mainRepository;
        }

        @Override
        protected Task<ObservableList<Customer>> createTask() {
            return new Task<>() {
                @Override
                protected ObservableList<Customer> call() throws Exception {
                    return mainRepository.getAllCustomers();
                }
            };
        }
    }

    public static class GetAppointmentsForUserByTimeFrameService extends Service<ObservableList<Appointment>> {

        private final MainRepository mainRepository;
        private final IntegerProperty userId = new SimpleIntegerProperty();
        private final ObjectProperty<ViewByTimeFrame> viewByTimeFrame = new SimpleObjectProperty<>();

        public GetAppointmentsForUserByTimeFrameService(MainRepository mainRepository) {
            this.mainRepository = mainRepository;
            this.userId.set(Constants.INVALID_ID);
            this.viewByTimeFrame.set(ViewByTimeFrame.WEEK);
        }

        public void setUserId(int userId) {
            this.userId.set(userId);
        }

        public void setViewByTimeFrame(ViewByTimeFrame viewByTimeFrame) {
            this.viewByTimeFrame.set(viewByTimeFrame);
        }

        @Override
        protected Task<ObservableList<Appointment>> createTask() {
            return new Task<>() {
                @Override
                protected ObservableList<Appointment> call() throws Exception {
                    if (userId.get() > Constants.INVALID_ID)
                        return mainRepository.getAppointmentsForUserByTimeFrame(userId.get(), viewByTimeFrame.get());
                    else
                        return FXCollections.emptyObservableList();
                }
            };
        }
    }

    public static class DeleteCustomerTask extends Task<Void> {

        private final MainRepository mainRepository;
        private final int customerId;

        public DeleteCustomerTask(MainRepository mainRepository, int customerId) {
            this.mainRepository = mainRepository;
            this.customerId = customerId;
        }

        @Override
        protected Void call() throws Exception {
            mainRepository.removeCustomer(customerId);
            return null;
        }
    }

    public static class DeleteAppointmentTask extends Task<Void> {

        private final MainRepository mainRepository;
        private final int appointmentId;

        public DeleteAppointmentTask(MainRepository mainRepository, int appointmentId) {
            this.mainRepository = mainRepository;
            this.appointmentId = appointmentId;
        }

        @Override
        protected Void call() throws Exception {
            mainRepository.removeAppointment(appointmentId);
            return null;
        }
    }
}