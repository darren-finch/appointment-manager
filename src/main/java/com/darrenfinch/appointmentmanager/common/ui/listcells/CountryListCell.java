package com.darrenfinch.appointmentmanager.common.ui.listcells;

import com.darrenfinch.appointmentmanager.common.data.entities.Country;
import javafx.scene.control.ListCell;

public class CountryListCell extends ListCell<Country> {
    @Override
    protected void updateItem(Country country, boolean empty) {
        super.updateItem(country, empty);
        if (!empty && country != null)
            setText(country.getName());
        else
            setText("");
    }
}
