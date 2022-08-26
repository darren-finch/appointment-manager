package com.darrenfinch.appointmentmanager.common;

public interface BaseController {
    // Provides a chance to clean-up resources before screen switch.
    default void onStopRequest() {};
}
