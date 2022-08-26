package com.darrenfinch.appointmentmanager.common.services;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.User;

public class UserManager {
    private final MainRepository mainRepository;
    private User currentUser = null;

    public UserManager(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public boolean loginWithUserNameAndPassword(String userName, String password) throws RuntimeException {
        try {
            User requestedUser = mainRepository.getUserByUserName(userName);
            if (requestedUser != null) {
                if (requestedUser.getPassword().equals(password)) {
                    currentUser = requestedUser;
                    return true;
                } else {
                    throw new RuntimeException("Incorrect password");
                }
            } else {
                throw new RuntimeException("Incorrect username");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
