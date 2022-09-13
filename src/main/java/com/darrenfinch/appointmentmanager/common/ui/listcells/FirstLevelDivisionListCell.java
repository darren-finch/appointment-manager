package com.darrenfinch.appointmentmanager.common.ui.listcells;

import com.darrenfinch.appointmentmanager.common.data.entities.FirstLevelDivision;
import javafx.scene.control.ListCell;

public class FirstLevelDivisionListCell extends ListCell<FirstLevelDivision> {
    @Override
    protected void updateItem(FirstLevelDivision firstLevelDivision, boolean empty) {
        super.updateItem(firstLevelDivision, empty);
        if (!empty && firstLevelDivision != null)
            setText(firstLevelDivision.getName());
        else
            setText("");
    }
}
