package com.darrenfinch.appointmentmanager.common.exceptions;

import com.darrenfinch.appointmentmanager.common.utils.Constants;

import java.util.ResourceBundle;

public class InvalidPasswordException extends Exception {
    /**
     * Gets the message of this exception.
     */
    @Override
    public String getMessage() {
        return ResourceBundle.getBundle(Constants.RESOURCE_BUNDLE_BASE_NAME).getString("invalid_password");
    }
}
