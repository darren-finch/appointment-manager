package com.darrenfinch.appointmentmanager.services;

import com.darrenfinch.appointmentmanager.data.MainRepository;
import com.darrenfinch.appointmentmanager.data.models.User;

public class UserManager {
    private final MainRepository repository;
    private User currentUser = null;

    UserManager(MainRepository repository) {
        this.repository = repository;
    }

    void loginWithUserNameAndPassword(String userName, String password) {
        // TODO: Implement with real database
//        currentUser = new User();
    }

    User getCurrentUser() {
        return currentUser;
    }
}
