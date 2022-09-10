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

    public UserManager(StringService stringService, LoginActivityLogger loginActivityLogger, TimeHelper timeHelper, MainRepository mainRepository) {
        this.stringService = stringService;
        this.loginActivityLogger = loginActivityLogger;
        this.timeHelper = timeHelper;
        this.mainRepository = mainRepository;
    }

    public boolean loginWithUserNameAndPassword(String userName, String password) throws RuntimeException {
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

    public User getCurrentUser() {
        return currentUser;
    }
}
