package com.darrenfinch.appointmentmanager.common.ui.listcells;

import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import javafx.scene.control.ListCell;

/**
 * A ListCell for Customers, used in ComboBoxes or ListViews.
 */
public class CustomerListCell extends ListCell<Customer> {
    /**
     * Sets the cell's text to the name of the customer, or if the cell is empty, it sets the text to an empty string.
     */
    @Override
    protected void updateItem(Customer customer, boolean empty) {
        super.updateItem(customer, empty);
        if (!empty && customer != null)
            setText(customer.getName());
        else
            setText("");
    }
}
