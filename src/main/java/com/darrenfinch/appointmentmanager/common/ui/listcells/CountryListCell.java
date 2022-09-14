package com.darrenfinch.appointmentmanager.common.ui.listcells;

import com.darrenfinch.appointmentmanager.common.data.entities.Country;
import javafx.scene.control.ListCell;

/**
 * A ListCell for Countries, used in ComboBoxes or ListViews.
 */
public class CountryListCell extends ListCell<Country> {
    /**
     * Sets the cell's text to the name of the country, or if the cell is empty, it sets the text to an empty string.
     */
    @Override
    protected void updateItem(Country country, boolean empty) {
        super.updateItem(country, empty);
        if (!empty && country != null)
            setText(country.getName());
        else
            setText("");
    }
}
