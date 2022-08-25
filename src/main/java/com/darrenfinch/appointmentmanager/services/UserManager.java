package com.darrenfinch.appointmentmanager.services;

import com.darrenfinch.appointmentmanager.data.MainRepository;
import com.darrenfinch.appointmentmanager.data.models.User;

import javax.security.auth.login.CredentialException;

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
                if (requestedUser.password().equals(password)) {
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
