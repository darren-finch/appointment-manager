package com.darrenfinch.appointmentmanager.common.ui.listcells;

import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import javafx.scene.control.ListCell;

public class CustomerListCell extends ListCell<Customer> {
    @Override
    protected void updateItem(Customer customer, boolean empty) {
        super.updateItem(customer, empty);
        if (!empty && customer != null)
            setText(customer.getName());
        else
            setText("");
    }
}
