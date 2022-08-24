package com.darrenfinch.appointmentmanager.di;

import com.darrenfinch.appointmentmanager.data.MainRepository;
import com.darrenfinch.appointmentmanager.data.MainRepositoryImpl;
import com.darrenfinch.appointmentmanager.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.services.DialogManager;
import com.darrenfinch.appointmentmanager.services.UserManager;
import com.mysql.jdbc.Driver;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class ApplicationConfig {
    private final ScreenNavigator screenNavigator;
    private final DialogManager dialogManager;

    private final Driver jdbcDriver;
    private final Connection dbConnection;
    private final MainRepository mainRepository;
    private final UserManager userManager;

    public ApplicationConfig(Stage stage) {
        this.screenNavigator = new ScreenNavigator(stage);
        this.dialogManager = new DialogManager();

        try {
            jdbcDriver = new Driver();
            // I am not doing this on a background thread yet to keep things simple.
            dbConnection = jdbcDriver.connect("", null);
            this.mainRepository = new MainRepositoryImpl(dbConnection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.userManager = new UserManager(mainRepository);
    }

    public ScreenNavigator getScreenNavigator() {
        return screenNavigator;
    }

    public DialogManager getDialogManager() {
        return dialogManager;
    }

    public Connection getDbConnection() {
        return dbConnection;
    }

    public MainRepository getMainRepository() {
        return mainRepository;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
