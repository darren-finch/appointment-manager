package com.darrenfinch.appointmentmanager.common.services;

import com.darrenfinch.appointmentmanager.common.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.*;

public class ScreenNavigator {

    private final ResourceBundle screenBundle;

    private final Stage stage;

    private final HashMap<String, Object> arguments = new HashMap<>();

    private BaseController currentController;

    private static HashMap<Class<?>, Callback<Class<?>, Object>> controllerBuilderMethods = new HashMap<>();

    private final Stack<String> screenStack = new Stack<>();

    /**
     * Constructs the screen navigator with a stage for displaying scenes and a resource bundle for localizing resources.
     */
    public ScreenNavigator(Stage stage, ResourceBundle screenBundle) {
        this.stage = stage;
        this.screenBundle = screenBundle;
    }

    /**
     * Sets a list of builder methods this screen navigator will use to build controllers with injected dependencies.
     * NOTE: These methods MUST be specified before switching to any screens, or the application will crash because the
     * screen navigator doesn't know how to build the controllers for each screen.
     */
    public static void setControllerBuilderMethods(HashMap<Class<?>, Callback<Class<?>, Object>> newControllerBuilderMethods) {
        controllerBuilderMethods = newControllerBuilderMethods;
    }

    /**
     * Switches to the login screen.
     */
    public void switchToLoginScreen() {
        switchToScreen("login.fxml");
    }

    /**
     * Switches to the dashboard screen.
     */
    public void switchToDashboardScreen() {
        switchToScreen("dashboard.fxml");
    }

    /**
     * Switches to the edit customer screen. If a valid customerId is provided, the customer with that id will be populated into the form.
     */
    public void switchToEditCustomerScreen(int customerId) {
        arguments.clear();
        arguments.put("customerId", customerId);
        switchToScreen("edit-customer.fxml");
    }

    /**
     * Switches to the edit appointment screen. If a valid appointmentId is provided, the appointment with that id will be populated into the form.
     */
    public void switchToEditAppointmentScreen(int appointmentId) {
        arguments.clear();
        arguments.put("appointmentId", appointmentId);
        switchToScreen("edit-appointment.fxml");
    }

    /**
     * Switches to the reports screen.
     */
    public void switchToReportsScreen() {
        switchToScreen("reports.fxml");
    }

    private void switchToScreen(String screenResourceName) {
        switchToScreen(screenResourceName, false);
    }

    /**
     * Handles the heavy lifting for the simple methods provided to the public.
     *
     * Also this method uses a lambda as a callback that creates controllers for the FXML loader via the controllerBuilderMethods property.
     * This was used to increase the readability of the code.
     */
    private void switchToScreen(String screenResourceName, boolean goingBack) {
        if (currentController != null) {
            // This allows the current controller to perform any cleanup necessary before going to the next screen.
            currentController.onStopRequest();
        }

        try {
            // Build a loader with the requested screen name, the screen bundle for localized resources, and a standard JavaFXBuilderFactory.
            // The final argument is the class of the controller to be loaded.
            // We get the class from the HashMap of builder methods specified in the setControllerBuilderMethods method.
            // If it doesn't exist, then throw an exception.
            JavaFXBuilderFactory javaFXBuilderFactory = new JavaFXBuilderFactory();
            FXMLLoader injectedLoader = new FXMLLoader(
                    ScreenNavigator.class.getResource("/" + screenResourceName),
                    screenBundle,
                    javaFXBuilderFactory,
                    (controllerClass) -> {
                        try {
                            if (controllerBuilderMethods.containsKey(controllerClass)) {
                                return controllerBuilderMethods.get(controllerClass).call(null);
                            } else {
                                throw new IllegalStateException("There is no builder method specified for controller " + controllerClass.getName());
                            }
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    });

            currentController = injectedLoader.getController();
            Parent root = injectedLoader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (ClassCastException ignored) {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // If we reach here the screen navigation was successful.
        // Push new screen to stack unless it's the same one that's on the stack, or we are going back.
        if (screenStack.isEmpty()) {
            screenStack.push(screenResourceName);
        } else if (!Objects.equals(screenStack.peek(), screenResourceName) && !goingBack) {
            screenStack.push(screenResourceName);
        }
    }

    /**
     * Gets a value from the argument map. Used for passing values between screens.
     */
    public Object getArgument(String key) {
        return arguments.get(key);
    }

    /**
     * Returns to the previous screen on the screen stack.
     */
    public void goBack() {
        if (!screenStack.isEmpty()) {
            screenStack.pop();
            switchToScreen(screenStack.peek(), true);
        }
    }
}