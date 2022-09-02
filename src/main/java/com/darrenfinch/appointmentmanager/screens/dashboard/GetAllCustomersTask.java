package com.darrenfinch.appointmentmanager.screens.dashboard;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class GetAllCustomersTask extends Task<ObservableList<Customer>> {

    private final MainRepository mainRepository;

    public GetAllCustomersTask(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    @Override
    protected ObservableList<Customer> call() throws Exception {
        return mainRepository.getAllCustomers();
    }
}
