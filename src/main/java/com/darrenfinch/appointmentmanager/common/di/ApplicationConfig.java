package com.darrenfinch.appointmentmanager.common.di;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.MainRepositoryImpl;
import com.darrenfinch.appointmentmanager.common.services.*;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import com.darrenfinch.appointmentmanager.screens.dashboard.DashboardController;
import com.darrenfinch.appointmentmanager.screens.dashboard.DashboardModel;
import com.darrenfinch.appointmentmanager.screens.editappointment.EditAppointmentController;
import com.darrenfinch.appointmentmanager.screens.editappointment.EditAppointmentModel;
import com.darrenfinch.appointmentmanager.screens.editcustomer.EditCustomerController;
import com.darrenfinch.appointmentmanager.screens.editcustomer.EditCustomerModel;
import com.darrenfinch.appointmentmanager.screens.login.LoginController;
import com.darrenfinch.appointmentmanager.screens.login.LoginModel;
import com.darrenfinch.appointmentmanager.screens.reports.ReportsController;
import com.darrenfinch.appointmentmanager.screens.reports.ReportsModel;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Holds the application's primary dependency graph.
 */
public class ApplicationConfig {
    private final StringService stringService;
    private final ScreenNavigator screenNavigator;
    private final DialogManager dialogManager;
    private final JDBCManager jdbcManager;
    private final MainRepository mainRepository;
    private final LoginActivityLogger loginActivityLogger;
    private final UserManager userManager;
    private final ExecutorService executorService;
    private final AppointmentAlertService appointmentAlertService;
    private final TimeHelper timeHelper;

    /**
     * Constructs the primary dependency graph of the application and sets up the database connection.
     */
    public ApplicationConfig(Stage stage) {
        ResourceBundle bundle = ResourceBundle.getBundle(Constants.RESOURCE_BUNDLE_BASE_NAME, Locale.getDefault());

        this.stringService = new StringService(bundle);
        this.screenNavigator = new ScreenNavigator(stage, bundle);
        this.dialogManager = new DialogManager();
        this.timeHelper = new TimeHelper();
        this.executorService = Executors.newCachedThreadPool();

        // I am not doing this on a background thread yet to keep things simple for now.
        jdbcManager = new JDBCManager();
        jdbcManager.openConnection();
        this.mainRepository = new MainRepositoryImpl(jdbcManager.getConnection(), getTimeHelper());
        this.mainRepository.initializeStaticData();

        this.loginActivityLogger = new LoginActivityLogger();
        this.userManager = new UserManager(getStringService(), getLoginActivityLogger(), getTimeHelper(), getMainRepository());

        this.appointmentAlertService = new AppointmentAlertService(getExecutorService(), getTimeHelper(), getDialogManager(), getMainRepository());

        setupControllerFactories();
    }

    private void setupControllerFactories() {
        HashMap<Class<?>, Callback<Class<?>, Object>> controllerBuilderMethods = new HashMap<>();
        controllerBuilderMethods.put(
                LoginController.class,
                p -> new LoginController(getStringService(), getScreenNavigator(), getUserManager(), getExecutorService(), getTimeHelper(), new LoginModel())
        );
        controllerBuilderMethods.put(
                DashboardController.class,
                p -> new DashboardController(getScreenNavigator(), getDialogManager(), getAppointmentAlertService(), getUserManager(), getExecutorService(), getMainRepository(), new DashboardModel())
        );
        controllerBuilderMethods.put(
                EditCustomerController.class,
                p -> new EditCustomerController(getScreenNavigator(), getDialogManager(), getUserManager(), getMainRepository(), getExecutorService(), new EditCustomerModel())
        );
        controllerBuilderMethods.put(
                EditAppointmentController.class,
                p -> new EditAppointmentController(getScreenNavigator(), getDialogManager(), getUserManager(), getMainRepository(), getExecutorService(), new EditAppointmentModel(getTimeHelper()))
        );
        controllerBuilderMethods.put(
                ReportsController.class,
                p -> new ReportsController(getScreenNavigator(), getExecutorService(), getMainRepository(), new ReportsModel())
        );

        ScreenNavigator.setControllerBuilderMethods(controllerBuilderMethods);
    }

    /**
     * Gets the string service.
     */
    public StringService getStringService() {
        return stringService;
    }

    /**
     * Gets the screen navigator.
     */
    public ScreenNavigator getScreenNavigator() {
        return screenNavigator;
    }

    /**
     * Gets the dialog manager.
     */
    public DialogManager getDialogManager() {
        return dialogManager;
    }

    /**
     * Gets the main repository.
     */
    public MainRepository getMainRepository() {
        return mainRepository;
    }

    /**
     * Gets the login activity manager.
     */
    public LoginActivityLogger getLoginActivityLogger() {
        return loginActivityLogger;
    }

    /**
     * Gets the user manager.
     */
    public UserManager getUserManager() {
        return userManager;
    }

    /**
     * Gets the executor service.
     */
    public ExecutorService getExecutorService() {
        return executorService;
    }

    /**
     * Gets the appointment alert service.
     */
    public AppointmentAlertService getAppointmentAlertService() {
        return appointmentAlertService;
    }

    /**
     * Gets the time helper.
     */
    public TimeHelper getTimeHelper() {
        return timeHelper;
    }

    /**
     * Cleans up any services that require a resource cleanup. This should be called before the application closes.
     */
    public void cleanup() {
        executorService.shutdown();
        jdbcManager.closeConnection();
    }
}
