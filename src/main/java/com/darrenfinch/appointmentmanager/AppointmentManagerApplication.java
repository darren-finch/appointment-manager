package com.darrenfinch.appointmentmanager;

import com.darrenfinch.appointmentmanager.di.ApplicationConfig;
import com.darrenfinch.appointmentmanager.di.ControllerDependencyInjector;
import com.darrenfinch.appointmentmanager.screens.dashboard.DashboardController;
import com.darrenfinch.appointmentmanager.screens.editappointment.EditAppointmentController;
import com.darrenfinch.appointmentmanager.screens.editcustomer.EditCustomerController;
import com.darrenfinch.appointmentmanager.screens.login.LoginController;
import com.darrenfinch.appointmentmanager.screens.reports.ReportsController;
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
                p -> new LoginController()
        );
        ControllerDependencyInjector.addInjectionMethod(
                DashboardController.class,
                p -> new DashboardController()
        );
        ControllerDependencyInjector.addInjectionMethod(
                EditCustomerController.class,
                p -> new EditCustomerController()
        );
        ControllerDependencyInjector.addInjectionMethod(
                EditAppointmentController.class,
                p -> new EditAppointmentController()
        );
        ControllerDependencyInjector.addInjectionMethod(
                ReportsController.class,
                p -> new ReportsController()
        );
    }

    public static void main(String[] args) {
        launch();
    }
}