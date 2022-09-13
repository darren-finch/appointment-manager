package com.darrenfinch.appointmentmanager.common.ui.listcells;

import com.darrenfinch.appointmentmanager.common.data.entities.User;
import javafx.scene.control.ListCell;

public class UserListCell extends ListCell<User> {
    @Override
    protected void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);
        if (!empty && user != null)
            setText(user.getName());
        else
            setText("");
    }
}
