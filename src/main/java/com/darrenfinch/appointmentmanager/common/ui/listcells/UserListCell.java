package com.darrenfinch.appointmentmanager.common.ui.listcells;

import com.darrenfinch.appointmentmanager.common.data.entities.User;
import javafx.scene.control.ListCell;

/**
 * A ListCell for Users, used in ComboBoxes or ListViews.
 */
public class UserListCell extends ListCell<User> {
    /**
     * Sets the cell's text to the name of the user, or if the cell is empty, it sets the text to an empty string.
     */
    @Override
    protected void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);
        if (!empty && user != null)
            setText(user.getName());
        else
            setText("");
    }
}
