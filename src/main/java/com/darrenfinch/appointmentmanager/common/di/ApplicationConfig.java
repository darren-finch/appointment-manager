package com.darrenfinch.appointmentmanager.common.di;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.MainRepositoryImpl;
import com.darrenfinch.appointmentmanager.common.services.*;
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationConfig {
    private final ScreenNavigator screenNavigator;
    private final DialogManager dialogManager;
    private final JDBCManager jdbcManager;
    private final MainRepository mainRepository;
    private final UserManager userManager;
    private final ExecutorService executorService;
    private final AppointmentAlertService appointmentAlertService;
    private final TimeHelper timeHelper;

    public ApplicationConfig(Stage stage) {
        this.screenNavigator = new ScreenNavigator(stage);
        this.dialogManager = new DialogManager();
        this.timeHelper = new TimeHelper();
        this.executorService = Executors.newCachedThreadPool();

        // I am not doing this on a background thread yet to keep things simple for now.
        jdbcManager = new JDBCManager();
        jdbcManager.openConnection();
        this.mainRepository = new MainRepositoryImpl(jdbcManager.getConnection(), getTimeHelper());
        this.mainRepository.initializeStaticData();

        this.userManager = new UserManager(getMainRepository());

        this.appointmentAlertService = new AppointmentAlertService(getExecutorService(), getTimeHelper(), getDialogManager(), getMainRepository());

        setupControllerFactories();
    }

    private void setupControllerFactories() {
        ControllerDependencyInjector.addInjectionMethod(
                LoginController.class,
                p -> new LoginController(getScreenNavigator(), getUserManager(), getExecutorService(), getTimeHelper(), new LoginModel())
        );
        ControllerDependencyInjector.addInjectionMethod(
                DashboardController.class,
                p -> new DashboardController(getScreenNavigator(), getDialogManager(), getAppointmentAlertService(), getUserManager(), getExecutorService(), getTimeHelper(), getMainRepository(), new DashboardModel())
        );
        ControllerDependencyInjector.addInjectionMethod(
                EditCustomerController.class,
                p -> new EditCustomerController(getScreenNavigator(), getDialogManager(), getUserManager(), getMainRepository(), getExecutorService(), new EditCustomerModel())
        );
        ControllerDependencyInjector.addInjectionMethod(
                EditAppointmentController.class,
                p -> new EditAppointmentController(getScreenNavigator(), getDialogManager(), getUserManager(), getMainRepository(), getExecutorService(), new EditAppointmentModel(getTimeHelper()))
        );
        ControllerDependencyInjector.addInjectionMethod(
                ReportsController.class,
                p -> new ReportsController(getScreenNavigator(), getExecutorService(), getMainRepository(), new ReportsModel())
        );
    }

    public ScreenNavigator getScreenNavigator() {
        return screenNavigator;
    }

    public DialogManager getDialogManager() {
        return dialogManager;
    }

    public JDBCManager getJdbcManager() {
        return jdbcManager;
    }

    public MainRepository getMainRepository() {
        return mainRepository;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public AppointmentAlertService getAppointmentAlertService() {
        return appointmentAlertService;
    }

    public TimeHelper getTimeHelper() {
        return timeHelper;
    }

    public void cleanup() {
        executorService.shutdown();
        jdbcManager.closeConnection();
    }
}
