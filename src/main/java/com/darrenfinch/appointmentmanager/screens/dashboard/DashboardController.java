package com.darrenfinch.appointmentmanager.screens.dashboard;

import com.darrenfinch.appointmentmanager.common.BaseController;
import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.data.misc.TimeFilter;
import com.darrenfinch.appointmentmanager.common.services.*;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

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
    @FXML
    private RadioButton viewByAllTimeRadioButton;

    private final GetAllCustomersService getAllCustomersService;
    private final GetAppointmentsForUserBySortingFilterService getAppointmentsForUserBySortingFilterService;

    /**
     * Constructs a new DashboardController with all the necessary dependencies.
     */
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

    /**
     * Sets up the initial data model, binds the view to the model, and starts any services that will fetch data from the database.
     *
     * Inside the implementation of this method, an inline lambda is used to filter the columns in the appointment table down to a single column named "Start".
     * Another inline lambda is used to filter the columns in the appointment table down to a single column named "End".
     *
     * These lambdas enhance readability since their parameters are self-explanatory.
     * Any other places a lambda could have been used, they were not used because the use of a lambda would have
     * caused potential confusion about the parameter types to the functional interface.
     */
    @FXML
    public void initialize() {
        appointmentAlertService.alertUserOfPotentialUpcomingAppointments(userManager.getCurrentUser().getId());

        // Initialize services
        getAllCustomersService.setExecutor(executorService);
        getAppointmentsForUserBySortingFilterService.setExecutor(executorService);
        getAppointmentsForUserBySortingFilterService.setAppointmentsSortingFilter(TimeFilter.WEEK);

        // Fill model
        model.customersProperty().bind(getAllCustomersService.valueProperty());
        model.appointmentsProperty().bind(getAppointmentsForUserBySortingFilterService.valueProperty());

        getAllCustomersService.start();
        getAppointmentsForUserBySortingFilterService.start();

        // Initialize radio buttons
        ToggleGroup toggleGroup = new ToggleGroup();
        viewByWeekRadioButton.setToggleGroup(toggleGroup);
        viewByMonthRadioButton.setToggleGroup(toggleGroup);
        viewByAllTimeRadioButton.setToggleGroup(toggleGroup);
        toggleGroup.selectToggle(viewByWeekRadioButton); // assuming that we will sort by week on screen load

        // Bind viewByProperty of model to re-fetch appointments
        model.timeFilterProperty().addListener(new ChangeListener<TimeFilter>() {
            @Override
            public void changed(ObservableValue<? extends TimeFilter> observableValue, TimeFilter oldVal, TimeFilter newVal) {
                getAppointmentsForUserBySortingFilterService.setAppointmentsSortingFilter(newVal);
                getAppointmentsForUserBySortingFilterService.restart();
            }
        });

        // Bind items of table views to model properties
        customersTableView.itemsProperty().bind(model.customersProperty());
        appointmentsTableView.itemsProperty().bind(model.appointmentsProperty());

        // Ensure date formatting is correct
        TableColumn<Appointment, String> startDateTimeColumn = (TableColumn<Appointment, String>) appointmentsTableView.getColumns().filtered(col -> col.getText().equals("Start")).get(0);
        startDateTimeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> appointmentStringCellDataFeatures) {
                SimpleStringProperty strProp = new SimpleStringProperty();
                strProp.set(appointmentStringCellDataFeatures.getValue().getStartDateTime().format(DateTimeFormatter.ofPattern(Constants.STANDARD_DATE_TIME_FORMAT)));
                return strProp;
            }
        });

        TableColumn<Appointment, String> endDateTimeColumn = (TableColumn<Appointment, String>) appointmentsTableView.getColumns().filtered(col -> col.getText().equals("End")).get(0);
        endDateTimeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> appointmentStringCellDataFeatures) {
                SimpleStringProperty strProp = new SimpleStringProperty();
                strProp.set(appointmentStringCellDataFeatures.getValue().getEndDateTime().format(DateTimeFormatter.ofPattern(Constants.STANDARD_DATE_TIME_FORMAT)));
                return strProp;
            }
        });
    }

    /**
     * Tries to delete the selected customer. If no customer is selected, it simply returns.
     * If one is selected, it will show a confirmation dialog, then upon confirmation it will try to delete the customer.
     * If the customer still has appointments assigned to him/her, then it will pop up a dialog stating that the customer's appointments must be deleted first.
     *
     * This function has a Runnable lambda to ease the cognitive load of reading this code and shorten the length of the method.
     */
    public void deleteCustomer() {
        CustomerWithLocationData selectedCustomerWithLocationData = customersTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomerWithLocationData == null)
            return;

        dialogManager.showConfirmationDialog(
                "Are you sure you want to delete this customer?",
                // Here is the lambda.
                () -> {
                    DeleteCustomerTask deleteCustomerTask = new DeleteCustomerTask(mainRepository, selectedCustomerWithLocationData.getCustomer().getId());
                    deleteCustomerTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent workerStateEvent) {
                            dialogManager.showAlertDialog("Customer with ID " + selectedCustomerWithLocationData.getCustomer().getId() + " was deleted successfully.");

                            getAllCustomersService.restart();
                            getAppointmentsForUserBySortingFilterService.restart();
                            workerStateEvent.consume();
                        }
                    });
                    deleteCustomerTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent workerStateEvent) {
                            dialogManager.showAlertDialog(workerStateEvent.getSource().getException().getMessage());
                            workerStateEvent.consume();
                        }
                    });
                    executorService.execute(deleteCustomerTask);
                },
                null
        );
    }

    /**
     * Tries to navigate to the edit customer screen with the selected customer. If no customer is selected, this method does nothing.
     */
    public void updateCustomer() {
        CustomerWithLocationData selectedCustomerWithLocationData = customersTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomerWithLocationData == null)
            return;

        screenNavigator.switchToEditCustomerScreen(selectedCustomerWithLocationData.getId());
    }

    /**
     * Navigates to the edit customer screen with an invalid id, which is the same as telling it that you are adding a new customer.
     */
    public void addCustomer() {
        screenNavigator.switchToEditCustomerScreen(Constants.INVALID_ID);
    }

    /**
     * Tries to delete the selected appointment. If no appointment is selected, it simply returns.
     * If one is selected, it will show a confirmation dialog, then upon confirmation it will try to delete the appointment.
     *
     * This function has a Runnable lambda to ease the cognitive load of reading this code and shorten the length of the method.
     * Any other places a lambda could have been used, they were not used because the use of a lambda would have
     * caused potential confusion about the parameter types to the functional interface.
     */
    public void deleteAppointment() {
        Appointment selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            dialogManager.showConfirmationDialog(
                    "Are you sure you want to delete this appointment?",
                    () -> {
                        DeleteAppointmentTask deleteAppointmentTask = new DeleteAppointmentTask(mainRepository, selectedAppointment.getId());
                        deleteAppointmentTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent workerStateEvent) {
                                getAppointmentsForUserBySortingFilterService.restart();
                                dialogManager.showAlertDialog(
                                        "Appointment was deleted successfully."
                                                + "\nID: " + selectedAppointment.getId()
                                                + "\nType: " + selectedAppointment.getType()
                                );
                                workerStateEvent.consume();
                            }
                        });
                        executorService.execute(deleteAppointmentTask);
                    },
                    null
            );
        }
    }

    /**
     * Tries to navigate to the edit appointment screen with the selected appointment. If no appointment is selected, it simply returns.
     */
    public void updateAppointment() {
        Appointment selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            screenNavigator.switchToEditAppointmentScreen(selectedAppointment.getId());
        }
    }

    /**
     * Navigates to the edit appointment screen with an invalid id, which is the same as telling it that you are adding a new appointment.
     */
    public void addAppointment() {
        screenNavigator.switchToEditAppointmentScreen(Constants.INVALID_ID);
    }

    /**
     * Navigates to the reports screen.
     */
    public void goToReportsScreen() {
        screenNavigator.switchToReportsScreen();
    }

    /**
     * Logs out the current user and navigates back to the login screen.
     */
    public void logout() {
        userManager.logout();
        screenNavigator.switchToLoginScreen();
    }

    /**
     * Called when the "This Week" radio is selected.
     * This changes the time filter of the model and causes the appointments list to update automatically thanks to data binding.
     */
    public void onViewByWeekSelected() {
        model.setTimeFilter(TimeFilter.WEEK);
    }

    /**
     * Called when the "This MONTH" radio is selected.
     * This changes the time filter of the model and causes the appointments list to update automatically thanks to data binding.
     */
    public void onViewByMonthSelected() {
        model.setTimeFilter(TimeFilter.MONTH);
    }

    /**
     * Called when the "All Time" radio is selected.
     * This changes the time filter of the model and causes the appointments list to update automatically thanks to data binding.
     */
    public void onViewByAllTimeSelected() {
        model.setTimeFilter(TimeFilter.ALL_TIME);
    }

    /**
     * Gets all customers from the database.
     */
    private static class GetAllCustomersService extends Service<ObservableList<CustomerWithLocationData>> {
        private final MainRepository mainRepository;

        public GetAllCustomersService(MainRepository mainRepository) {
            this.mainRepository = mainRepository;
        }

        @Override
        protected Task<ObservableList<CustomerWithLocationData>> createTask() {
            return new Task<>() {
                @Override
                protected ObservableList<CustomerWithLocationData> call() {
                    return mainRepository.getAllCustomersWithLocationData();
                }
            };
        }
    }

    /**
     * Gets all appointments from the database, filtered by the currently selected time filter.
     */
    private static class GetAppointmentsForUserBySortingFilterService extends Service<ObservableList<Appointment>> {

        private final MainRepository mainRepository;
        private final ObjectProperty<TimeFilter> appointmentsSortingFilter = new SimpleObjectProperty<>();

        public GetAppointmentsForUserBySortingFilterService(MainRepository mainRepository) {
            this.mainRepository = mainRepository;
            this.appointmentsSortingFilter.set(TimeFilter.WEEK);
        }

        public void setAppointmentsSortingFilter(TimeFilter timeFilter) {
            this.appointmentsSortingFilter.set(timeFilter);
        }

        @Override
        protected Task<ObservableList<Appointment>> createTask() {
            return new Task<>() {
                @Override
                protected ObservableList<Appointment> call() {
                    return mainRepository.getAppointmentsByTimeFilter(appointmentsSortingFilter.get());
                }
            };
        }
    }

    /**
     * Tries to delete a customer from the database. It will throw a CustomerHasAppointmentsException if it tries to delete a customer with assigned appointments.
     */
    private static class DeleteCustomerTask extends Task<Void> {

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

    /**
     * Deletes an appointment from the database.
     */
    private static class DeleteAppointmentTask extends Task<Void> {

        private final MainRepository mainRepository;
        private final int appointmentId;

        public DeleteAppointmentTask(MainRepository mainRepository, int appointmentId) {
            this.mainRepository = mainRepository;
            this.appointmentId = appointmentId;
        }

        @Override
        protected Void call() {
            mainRepository.removeAppointment(appointmentId);
            return null;
        }
    }
}