package com.darrenfinch.appointmentmanager.common.ui.listcells;

import com.darrenfinch.appointmentmanager.common.utils.TimeFilter;
import javafx.scene.control.ListCell;

public class TimeFilterListCell extends ListCell<TimeFilter> {
    @Override
    protected void updateItem(TimeFilter timeFilter, boolean empty) {
        super.updateItem(timeFilter, empty);
        if (!empty && timeFilter != null)
            setText(timeFilter.toString());
        else
            setText("");
    }
}
