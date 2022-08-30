package com.darrenfinch.appointmentmanager.common.di;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.MainRepositoryImpl;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.common.services.DialogManager;
import com.darrenfinch.appointmentmanager.common.services.UserManager;
import com.darrenfinch.appointmentmanager.common.services.JDBCManager;
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

public class ApplicationConfig {
    private final ScreenNavigator screenNavigator;
    private final DialogManager dialogManager;

    private final JDBCManager jdbcManager;
    private final MainRepository mainRepository;
    private final UserManager userManager;

    public ApplicationConfig(Stage stage) {
        this.screenNavigator = new ScreenNavigator(stage);
        this.dialogManager = new DialogManager();

        // I am not doing this on a background thread yet to keep things simple for now.
        jdbcManager = new JDBCManager();
        jdbcManager.openConnection();
        this.mainRepository = new MainRepositoryImpl(jdbcManager.getConnection());
        this.mainRepository.initializeStaticData();

        this.userManager = new UserManager(mainRepository);

        setupControllerFactories();
    }

    private void setupControllerFactories() {
        ControllerDependencyInjector.addInjectionMethod(
                LoginController.class,
                p -> new LoginController(getScreenNavigator(), getUserManager(), new LoginModel())
        );
        ControllerDependencyInjector.addInjectionMethod(
                DashboardController.class,
                p -> new DashboardController(getScreenNavigator(), getDialogManager(), getUserManager(), getMainRepository(), new DashboardModel())
        );
        ControllerDependencyInjector.addInjectionMethod(
                EditCustomerController.class,
                p -> new EditCustomerController(getScreenNavigator(), getDialogManager(), getMainRepository(), new EditCustomerModel(getMainRepository()))
        );
        ControllerDependencyInjector.addInjectionMethod(
                EditAppointmentController.class,
                p -> new EditAppointmentController(getScreenNavigator(), getDialogManager(), new EditAppointmentModel())
        );
        ControllerDependencyInjector.addInjectionMethod(
                ReportsController.class,
                p -> new ReportsController(getScreenNavigator(), new ReportsModel())
        );
    }

    public ScreenNavigator getScreenNavigator() {
        return screenNavigator;
    }

    public DialogManager getDialogManager() {
        return dialogManager;
    }

    public MainRepository getMainRepository() {
        return mainRepository;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void cleanup() {
        jdbcManager.closeConnection();
    }
}
