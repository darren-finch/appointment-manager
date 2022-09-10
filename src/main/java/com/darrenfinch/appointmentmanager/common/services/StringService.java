package com.darrenfinch.appointmentmanager.common.services;

import java.util.Locale;
import java.util.ResourceBundle;

public class StringService {
    private final ResourceBundle resourceBundle;

    public StringService(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }
}
