package com.darrenfinch.appointmentmanager.screens.login;

import com.darrenfinch.appointmentmanager.common.BaseController;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.common.services.StringService;
import com.darrenfinch.appointmentmanager.common.services.TimeHelper;
import com.darrenfinch.appointmentmanager.common.services.UserManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    private final StringService stringService;
    private final ScreenNavigator screenNavigator;
    private final UserManager userManager;
    private final ExecutorService executorService;
    private final TimeHelper timeHelper;
    private final LoginModel model;

    /**
     * Constructs a new LoginController with all the necessary dependencies.
     */
    public LoginController(
            StringService stringService,
            ScreenNavigator screenNavigator,
            UserManager userManager,
            ExecutorService executorService,
            TimeHelper timeHelper,
            LoginModel model
    ) {
        this.stringService = stringService;
        this.screenNavigator = screenNavigator;
        this.userManager = userManager;
        this.executorService = executorService;
        this.timeHelper = timeHelper;
        this.model = model;
    }

    /**
     * Sets up the initial data model and binds the view to the model. This screen has no background services to start on initialize.
     */
    @FXML
    public void initialize() {
        locationLabel.textProperty().bind(model.locationProperty());
        userNameTextField.textProperty().bindBidirectional(model.userNameProperty());
        passwordPasswordField.textProperty().bindBidirectional(model.passwordProperty());
        errorLabel.textProperty().bind(model.errorProperty());

        model.setLocation(String.format(stringService.getString("location_display"), timeHelper.defaultZone().getId()));
    }

    /**
     * Tries to log in using the provided username and password.
     * If these credentials are correct, the user manager will log in with the provided user credentials, and we will navigate to the dashboard screen.
     * If these credentials are incorrect, a descriptive error message will be displayed below the login form.
     *
     * Inside the implementation of this method, a lambda is used to switch to the dashboard screen upon a successful login.
     * This lambda was used because it enhances readability, shortens the code,
     * and there is no parameter for the {@link ScreenNavigator#switchToDashboardScreen ScreenNavigator.switchToDashboardScreen} method, so it's easy to justify a lambda.
     *
     * Another lambda is also used to set the error property on the data model upon an unsuccessful login.
     * Once again, this lambda enhances readability and shortens the code.
     */
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