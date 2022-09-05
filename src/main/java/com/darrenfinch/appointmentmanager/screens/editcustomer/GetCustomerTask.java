package com.darrenfinch.appointmentmanager.screens.editcustomer;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import javafx.concurrent.Task;

public class GetCustomerTask extends Task<Customer> {
    private final MainRepository mainRepository;
    private final int customerId;

    public GetCustomerTask(MainRepository mainRepository, int customerId) {
        this.mainRepository = mainRepository;
        this.customerId = customerId;
    }

    @Override
    protected Customer call() throws Exception {
        if (customerId != Constants.INVALID_ID) {
            return mainRepository.getCustomer(customerId);
        } else {
            throw new RuntimeException("Invalid customer ID.");
        }
    }
}
