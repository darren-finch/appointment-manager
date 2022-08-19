package com.darrenfinch.appointmentmanager.services;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DialogManager {
    private boolean showingDialog = false;

    public void showExitConfirmationDialog(Runnable onOk, Runnable onCancel) {
        showConfirmationDialog("Are you sure you want to quit?", onOk, onCancel);
    }

    public void showDeleteConfirmationDialog(Runnable onOk, Runnable onCancel) {
        showConfirmationDialog("Are you sure you want to delete?", onOk, onCancel);
    }

    public void showCancelConfirmationDialog(Runnable onOk, Runnable onCancel) {
        showConfirmationDialog("Are you sure you want to cancel?", onOk, onCancel);
    }

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
