package com.darrenfinch.appointmentmanager.services;

import com.darrenfinch.appointmentmanager.data.MainRepository;
import com.darrenfinch.appointmentmanager.data.models.User;

public class UserManager {
    private final MainRepository mainRepository;
    private User currentUser = null;

    public UserManager(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public boolean loginWithUserNameAndPassword(String userName, String password) {
        User requestedUser = mainRepository.getUserByUserName(userName);
        if (requestedUser.password().equals(password)) {
            currentUser = requestedUser;
            return true;
        } else {
            return false;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
