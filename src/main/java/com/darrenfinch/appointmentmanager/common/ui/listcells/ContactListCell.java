package com.darrenfinch.appointmentmanager.common.ui.listcells;

import com.darrenfinch.appointmentmanager.common.data.entities.Contact;
import javafx.scene.control.ListCell;

public class ContactListCell extends ListCell<Contact> {
    @Override
    protected void updateItem(Contact contact, boolean empty) {
        super.updateItem(contact, empty);
        if (!empty && contact != null)
            setText(contact.getName());
        else
            setText("");
    }
}
