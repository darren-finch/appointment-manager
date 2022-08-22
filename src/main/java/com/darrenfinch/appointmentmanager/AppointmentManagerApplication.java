package com.darrenfinch.appointmentmanager;

import com.darrenfinch.appointmentmanager.di.ApplicationConfig;
import com.darrenfinch.appointmentmanager.di.ControllerDependencyInjector;
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

public class AppointmentManagerApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) {
        ApplicationConfig config = new ApplicationConfig(stage);
        setupControllerFactories(config);

        config.getScreenNavigator().switchToLoginScreen();
    }

    private void setupControllerFactories(ApplicationConfig config) {
        ControllerDependencyInjector.addInjectionMethod(
                LoginController.class,
                p -> new LoginController(config.getScreenNavigator(), new LoginModel())
        );
        ControllerDependencyInjector.addInjectionMethod(
                DashboardController.class,
                p -> new DashboardController(config.getScreenNavigator(), config.getDialogManager(), new DashboardModel())
        );
        ControllerDependencyInjector.addInjectionMethod(
                EditCustomerController.class,
                p -> new EditCustomerController(config.getScreenNavigator(), new EditCustomerModel())
        );
        ControllerDependencyInjector.addInjectionMethod(
                EditAppointmentController.class,
                p -> new EditAppointmentController(config.getScreenNavigator(), new EditAppointmentModel())
        );
        ControllerDependencyInjector.addInjectionMethod(
                ReportsController.class,
                p -> new ReportsController(config.getScreenNavigator(), new ReportsModel())
        );
    }

    public static void main(String[] args) {
        launch();
    }
}