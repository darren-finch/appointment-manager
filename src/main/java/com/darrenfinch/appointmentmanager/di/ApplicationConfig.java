package com.darrenfinch.appointmentmanager.di;

import com.darrenfinch.appointmentmanager.data.MainRepository;
import com.darrenfinch.appointmentmanager.data.MainRepositoryImpl;
import com.darrenfinch.appointmentmanager.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.services.DialogManager;
import com.darrenfinch.appointmentmanager.services.UserManager;
import com.darrenfinch.appointmentmanager.services.JDBCManager;
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

        this.userManager = new UserManager(mainRepository);
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
