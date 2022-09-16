package com.darrenfinch.appointmentmanager.common.services;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.User;
import com.darrenfinch.appointmentmanager.common.exceptions.InvalidPasswordException;
import com.darrenfinch.appointmentmanager.common.exceptions.UserNotFoundException;
import com.darrenfinch.appointmentmanager.common.utils.Constants;

import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class UserManager {
    private final LoginActivityLogger loginActivityLogger;
    private final TimeHelper timeHelper;
    private final MainRepository mainRepository;
    private User currentUser = null;

    /**
     * Constructs the user manager.
     */
    public UserManager(LoginActivityLogger loginActivityLogger, TimeHelper timeHelper, MainRepository mainRepository) {
        this.loginActivityLogger = loginActivityLogger;
        this.timeHelper = timeHelper;
        this.mainRepository = mainRepository;
    }

    /**
     * Attempts to log in with the specified username and password.
     * If the login is unsuccessful, it will throw an Exception with the reason for the login failure.
     */
    public boolean loginWithUserNameAndPassword(String userName, String password) throws UserNotFoundException, InvalidPasswordException {
        User requestedUser = mainRepository.getUserByUserName(userName);

        if (!requestedUser.getPassword().equals(password)) {
            loginActivityLogger.writeLoginAttempt(userName, timeHelper.systemTimeNow(), false);
            throw new InvalidPasswordException();
        }

        currentUser = requestedUser;
        loginActivityLogger.writeLoginAttempt(userName, timeHelper.systemTimeNow(), true);
        return true;
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
