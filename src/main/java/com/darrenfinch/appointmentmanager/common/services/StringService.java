package com.darrenfinch.appointmentmanager.common.services;

import java.util.Locale;
import java.util.ResourceBundle;

public class StringService {
    private final ResourceBundle resourceBundle;

    /**
     * Constructs the string service with a resource bundle.
     */
    public StringService(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    /**
     * Gets a string from the resource bundle.
     */
    public String getString(String key) {
        return resourceBundle.getString(key);
    }
}
