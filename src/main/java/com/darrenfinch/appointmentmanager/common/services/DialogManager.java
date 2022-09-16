package com.darrenfinch.appointmentmanager.common.services;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DialogManager {
    private boolean showingDialog = false;

    /**
     * Shows a dialog with a prompt, an okay button, and a cancel button.
     *
     * This method uses a lambda for a dialog callback because it was much easier to understand and read than a full class.
     */
    public void showConfirmationDialog(String confirmationPrompt, Runnable onOk, Runnable onCancel) {
        if (showingDialog) {
            return;
        }

        Alert alertDialog = new Alert(Alert.AlertType.CONFIRMATION, confirmationPrompt);
        showingDialog = true;
        alertDialog.showAndWait().ifPresent(response -> {
            // This needs to be set to false before we run any responses to allow for dialog chaining
            showingDialog = false;
            if (response.equals(ButtonType.OK) && onOk != null) {
                onOk.run();
            } else if (response.equals(ButtonType.CANCEL) && onCancel != null) {
                onCancel.run();
            }
        });
    }

    /**
     * Shows a dialog with a prompt and an okay button.
     *
     * This method uses a lambda for a dialog callback because it was much easier to understand and read than a full class.
     */
    public void showAlertDialog(String alertText) {
        if (showingDialog)
            return;

        Alert alertDialog = new Alert(Alert.AlertType.INFORMATION, alertText);
        showingDialog = true;
        alertDialog.showAndWait().ifPresent(response -> {
            showingDialog = false;
        });
    }
}
