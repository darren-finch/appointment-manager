package com.darrenfinch.appointmentmanager.common.ui.listcells;

import com.darrenfinch.appointmentmanager.common.data.entities.Contact;
import javafx.scene.control.ListCell;

/**
 * A ListCell for Contacts, used in ComboBoxes or ListViews.
 */
public class ContactListCell extends ListCell<Contact> {
    /**
     * Sets the cell's text to the name of the contact, or if the cell is empty, it sets the text to an empty string.
     */
    @Override
    protected void updateItem(Contact contact, boolean empty) {
        super.updateItem(contact, empty);
        if (!empty && contact != null)
            setText(contact.getName());
        else
            setText("");
    }
}
