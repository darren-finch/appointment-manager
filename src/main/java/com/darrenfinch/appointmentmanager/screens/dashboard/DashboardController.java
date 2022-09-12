package com.darrenfinch.appointmentmanager.screens.dashboard;

import com.darrenfinch.appointmentmanager.common.BaseController;
import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.services.*;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;

public class DashboardController implements BaseController {
    private final ScreenNavigator screenNavigator;
    private final DialogManager dialogManager;
    private final AppointmentAlertService appointmentAlertService;
    private final UserManager userManager;
    private final ExecutorService executorService;
    private final MainRepository mainRepository;
    private final DashboardModel model;
    @FXML
    private TableView<CustomerWithLocationData> customersTableView;
    @FXML
    private TableView<Appointment> appointmentsTableView;
    @FXML
    private RadioButton viewByWeekRadioButton;
    @FXML
    private RadioButton viewByMonthRadioButton;

    private final GetAllCustomersService getAllCustomersService;
    private final GetAppointmentsForUserBySortingFilterService getAppointmentsForUserBySortingFilterService;

    public DashboardController(
            ScreenNavigator screenNavigator,
            DialogManager dialogManager,
            AppointmentAlertService appointmentAlertService,
            UserManager userManager,
            ExecutorService executorService,
            MainRepository mainRepository,
            DashboardModel model
    ) {
        this.screenNavigator = screenNavigator;
        this.dialogManager = dialogManager;
        this.appointmentAlertService = appointmentAlertService;
        this.userManager = userManager;
        this.executorService = executorService;
        this.mainRepository = mainRepository;
        this.model = model;
        this.getAllCustomersService = new GetAllCustomersService(mainRepository);
        this.getAppointmentsForUserBySortingFilterService = new GetAppointmentsForUserBySortingFilterService(mainRepository);
    }

    @FXML
    public void initialize() {
        appointmentAlertService.alertUserOfPotentialUpcomingAppointments(userManager.getCurrentUser().getId());

        // Initialize services
        getAllCustomersService.setExecutor(executorService);
        getAppointmentsForUserBySortingFilterService.setExecutor(executorService);
        getAppointmentsForUserBySortingFilterService.setUserId(userManager.getCurrentUser().getId());
        getAppointmentsForUserBySortingFilterService.setAppointmentsSortingFilter(AppointmentsSortingFilter.WEEK);

        // Fill model
        model.customersProperty().bind(getAllCustomersService.valueProperty());
        model.appointmentsProperty().bind(getAppointmentsForUserBySortingFilterService.valueProperty());

        getAllCustomersService.start();
        getAppointmentsForUserBySortingFilterService.start();

        // Initialize radio buttons
        ToggleGroup toggleGroup = new ToggleGroup();
        viewByWeekRadioButton.setToggleGroup(toggleGroup);
        viewByMonthRadioButton.setToggleGroup(toggleGroup);
        toggleGroup.selectToggle(viewByWeekRadioButton); // assuming that we will sort by week on screen load

        // Bind viewByProperty of model to re-fetch appointments
        model.viewByProperty().addListener((obs, oldVal, newVal) -> {
            getAppointmentsForUserBySortingFilterService.setAppointmentsSortingFilter(newVal);
            getAppointmentsForUserBySortingFilterService.restart();
        });

        // Bind items of table views to model properties
        customersTableView.itemsProperty().bind(model.customersProperty());
        appointmentsTableView.itemsProperty().bind(model.appointmentsProperty());

        // Ensure date formatting is correct
        TableColumn<Appointment, String> startDateTimeColumn = (TableColumn<Appointment, String>) appointmentsTableView.getColumns().filtered(col -> col.getText().equals("Start")).get(0);
        startDateTimeColumn.setCellValueFactory(appointmentStringCellDataFeatures -> {
            SimpleStringProperty strProp = new SimpleStringProperty();
            strProp.set(appointmentStringCellDataFeatures.getValue().getStartDateTime().format(DateTimeFormatter.ofPattern(Constants.STANDARD_DATE_TIME_FORMAT)));
            return strProp;
        });

        TableColumn<Appointment, String> endDateTimeColumn = (TableColumn<Appointment, String>) appointmentsTableView.getColumns().filtered(col -> col.getText().equals("End")).get(0);
        endDateTimeColumn.setCellValueFactory(appointmentStringCellDataFeatures -> {
            SimpleStringProperty strProp = new SimpleStringProperty();
            strProp.set(appointmentStringCellDataFeatures.getValue().getEndDateTime().format(DateTimeFormatter.ofPattern(Constants.STANDARD_DATE_TIME_FORMAT)));
            return strProp;
        });
    }

    public void deleteCustomer() {
        CustomerWithLocationData selectedCustomerWithLocationData = customersTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomerWithLocationData == null)
            return;

        dialogManager.showConfirmationDialog(
                "Are you sure you want to delete this customer?",
                () -> {
                    DeleteCustomerTask deleteCustomerTask = new DeleteCustomerTask(mainRepository, selectedCustomerWithLocationData.getCustomer().getId());
                    deleteCustomerTask.setOnSucceeded(workerStateEvent -> {
                        dialogManager.showAlertDialog("Customer with ID " + selectedCustomerWithLocationData.getCustomer().getId() + " was deleted successfully.");

                        getAllCustomersService.restart();
                        getAppointmentsForUserBySortingFilterService.restart();
                        workerStateEvent.consume();
                    });
                    deleteCustomerTask.setOnFailed(workerStateEvent -> {
                        dialogManager.showAlertDialog(workerStateEvent.getSource().getException().getMessage());
                        workerStateEvent.consume();
                    });
                    executorService.execute(deleteCustomerTask);
                },
                null
        );
    }

    public void updateCustomer() {
        CustomerWithLocationData selectedCustomerWithLocationData = customersTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomerWithLocationData == null)
            return;

        screenNavigator.switchToEditCustomerScreen(selectedCustomerWithLocationData.getId());
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
                            getAppointmentsForUserBySortingFilterService.restart();
                            dialogManager.showAlertDialog(
                                    "Appointment was deleted successfully."
                                    + "\nID: " + selectedAppointment.getId()
                                    + "\nType: " + selectedAppointment.getType()
                            );
                            workerStateEvent.consume();
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

    public void goToReportsScreen() {
        screenNavigator.switchToReportsScreen();
    }

    public void logout() {
        userManager.logout();
        screenNavigator.switchToLoginScreen();
    }

    public void onViewByWeekSelected() {
        model.setViewBy(AppointmentsSortingFilter.WEEK);
    }

    public void onViewByMonthSelected() {
        model.setViewBy(AppointmentsSortingFilter.MONTH);
    }

    public enum AppointmentsSortingFilter {
        WEEK("Week"),
        MONTH("Month");

        private final String name;

        AppointmentsSortingFilter(String s) {
            name = s;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class GetAllCustomersService extends Service<ObservableList<CustomerWithLocationData>> {
        private final MainRepository mainRepository;

        public GetAllCustomersService(MainRepository mainRepository) {
            this.mainRepository = mainRepository;
        }

        @Override
        protected Task<ObservableList<CustomerWithLocationData>> createTask() {
            return new Task<>() {
                @Override
                protected ObservableList<CustomerWithLocationData> call() throws Exception {
                    return mainRepository.getAllCustomersWithLocationData();
                }
            };
        }
    }

    public static class GetAppointmentsForUserBySortingFilterService extends Service<ObservableList<Appointment>> {

        private final MainRepository mainRepository;
        private final IntegerProperty userId = new SimpleIntegerProperty();
        private final ObjectProperty<AppointmentsSortingFilter> appointmentsSortingFilter = new SimpleObjectProperty<>();

        public GetAppointmentsForUserBySortingFilterService(MainRepository mainRepository) {
            this.mainRepository = mainRepository;
            this.userId.set(Constants.INVALID_ID);
            this.appointmentsSortingFilter.set(AppointmentsSortingFilter.WEEK);
        }

        public void setUserId(int userId) {
            this.userId.set(userId);
        }

        public void setAppointmentsSortingFilter(AppointmentsSortingFilter appointmentsSortingFilter) {
            this.appointmentsSortingFilter.set(appointmentsSortingFilter);
        }

        @Override
        protected Task<ObservableList<Appointment>> createTask() {
            return new Task<>() {
                @Override
                protected ObservableList<Appointment> call() throws Exception {
                    return mainRepository.getAppointmentsForUserBySortingFilter(userId.get(), appointmentsSortingFilter.get());
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