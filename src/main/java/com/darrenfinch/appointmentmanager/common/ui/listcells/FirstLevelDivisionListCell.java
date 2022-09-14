package com.darrenfinch.appointmentmanager.common.ui.listcells;

import com.darrenfinch.appointmentmanager.common.data.entities.FirstLevelDivision;
import javafx.scene.control.ListCell;

/**
 * A ListCell for FirstLevelDivisions, used in ComboBoxes or ListViews.
 */
public class FirstLevelDivisionListCell extends ListCell<FirstLevelDivision> {
    /**
     * Sets the cell's text to the name of the first level division, or if the cell is empty, it sets the text to an empty string.
     */
    @Override
    protected void updateItem(FirstLevelDivision firstLevelDivision, boolean empty) {
        super.updateItem(firstLevelDivision, empty);
        if (!empty && firstLevelDivision != null)
            setText(firstLevelDivision.getName());
        else
            setText("");
    }
}
