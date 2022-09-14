package com.darrenfinch.appointmentmanager.common.services;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.User;
import com.darrenfinch.appointmentmanager.common.utils.Constants;

import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class UserManager {
    private final StringService stringService;
    private final LoginActivityLogger loginActivityLogger;
    private final TimeHelper timeHelper;
    private final MainRepository mainRepository;
    private User currentUser = null;

    /**
     * Constructs the user manager.
     */
    public UserManager(StringService stringService, LoginActivityLogger loginActivityLogger, TimeHelper timeHelper, MainRepository mainRepository) {
        this.stringService = stringService;
        this.loginActivityLogger = loginActivityLogger;
        this.timeHelper = timeHelper;
        this.mainRepository = mainRepository;
    }

    /**
     * Attempts to log in with the specified username and password.
     * If the login is unsuccessful, it will throw an Exception with the reason for the login failure.
     */
    // TODO REFACTOR WITH CUSTOM EXCEPTION
    public boolean loginWithUserNameAndPassword(String userName, String password) throws Exception {
        try {
            User requestedUser = mainRepository.getUserByUserName(userName);

            if (requestedUser == null) {
                loginActivityLogger.writeLoginAttempt(userName, timeHelper.systemTimeNow(), false);
                throw new RuntimeException(stringService.getString("invalid_username"));
            }

            if (!requestedUser.getPassword().equals(password)) {
                loginActivityLogger.writeLoginAttempt(userName, timeHelper.systemTimeNow(), false);
                throw new RuntimeException(stringService.getString("invalid_password"));
            }

            currentUser = requestedUser;
            loginActivityLogger.writeLoginAttempt(userName, timeHelper.systemTimeNow(), true);
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * Logs out the current user.
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * Returns the current user.
     */
    public User getCurrentUser() {
        return currentUser;
    }
}
