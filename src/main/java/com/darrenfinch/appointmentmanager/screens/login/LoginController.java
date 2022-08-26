package com.darrenfinch.appointmentmanager.screens.login;

import com.darrenfinch.appointmentmanager.common.BaseController;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.common.services.UserManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    private final LoginModel model;

    public LoginController(ScreenNavigator screenNavigator, UserManager userManager, LoginModel model) {
        this.screenNavigator = screenNavigator;
        this.userManager = userManager;
        this.model = model;
    }

    @FXML
    public void initialize() {
        locationLabel.textProperty().bind(model.locationProperty());
        userNameTextField.textProperty().bindBidirectional(model.userNameProperty());
        passwordPasswordField.textProperty().bindBidirectional(model.passwordProperty());
        errorLabel.textProperty().bind(model.errorProperty());
    }

    @FXML
    public void login() {
        Task<Boolean> loginTask = new Task<>() {
            @Override
            protected Boolean call() {
                try {
                    if(userManager.loginWithUserNameAndPassword(model.getUserName(), model.getPassword())) {
                        Platform.runLater(screenNavigator::switchToDashboardScreen);
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    Platform.runLater(() -> {
                        model.errorProperty().set(e.getLocalizedMessage());
                    });
                }

                return false;
            }
        };

        new Thread(loginTask).start();
    }
}