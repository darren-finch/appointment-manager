package com.darrenfinch.appointmentmanager.common.ui.listcells;

import com.darrenfinch.appointmentmanager.common.data.misc.TimeFilter;
import javafx.scene.control.ListCell;

/**
 * A ListCell for TimeFilters, used in ComboBoxes or ListViews.
 */
public class TimeFilterListCell extends ListCell<TimeFilter> {
    /**
     * Sets the cell's text to the time filter as a string, or if the cell is empty, it sets the text to an empty string.
     */
    @Override
    protected void updateItem(TimeFilter timeFilter, boolean empty) {
        super.updateItem(timeFilter, empty);
        if (!empty && timeFilter != null)
            setText(timeFilter.toString());
        else
            setText("");
    }
}
