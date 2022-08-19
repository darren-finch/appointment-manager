package com.darrenfinch.appointmentmanager.controllers;

import com.darrenfinch.appointmentmanager.di.BaseControllerConfig;

public abstract class BaseController {
    private BaseControllerConfig controllerConfig;

    public final void setupConfig(BaseControllerConfig controllerConfig) {
        if (controllerConfig == null) {
            throw new RuntimeException("BaseControllerConfig not initialized!");
        }

        this.controllerConfig = controllerConfig;
    }

    public abstract void setupUI();

    public BaseControllerConfig getConfig() {
        return controllerConfig;
    }
}
