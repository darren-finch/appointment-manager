package com.darrenfinch.appointmentmanager.screens.login;

import com.darrenfinch.appointmentmanager.common.BaseController;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.common.services.TimeHelper;
import com.darrenfinch.appointmentmanager.common.services.UserManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.concurrent.ExecutorService;

public class LoginController implements BaseController {
    @FXML
    private Label locationLabel;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Label errorLabel;

    private final ScreenNavigator screenNavigator;
    private final UserManager userManager;
    private final ExecutorService executorService;
    private final TimeHelper timeHelper;
    private final LoginModel model;

    public LoginController(ScreenNavigator screenNavigator, UserManager userManager, ExecutorService executorService, TimeHelper timeHelper, LoginModel model) {
        this.screenNavigator = screenNavigator;
        this.userManager = userManager;
        this.executorService = executorService;
        this.timeHelper = timeHelper;
        this.model = model;
    }

    @FXML
    public void initialize() {
        locationLabel.textProperty().bind(model.locationProperty());
        userNameTextField.textProperty().bindBidirectional(model.userNameProperty());
        passwordPasswordField.textProperty().bindBidirectional(model.passwordProperty());
        errorLabel.textProperty().bind(model.errorProperty());

        model.setLocation("You are in the " + timeHelper.defaultZone().getId() + " time zone.");
    }

    @FXML
    public void login() {
        executorService.execute(new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                try {
                    if(userManager.loginWithUserNameAndPassword(model.getUserName(), model.getPassword())) {
                        Platform.runLater(screenNavigator::switchToDashboardScreen);
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    Platform.runLater(() -> {
                        model.setError(e.getLocalizedMessage());
                    });
                }

                return false;
            }
        });
    }
}