package com.darrenfinch.appointmentmanager.common.utils.formatters;

import javafx.scene.control.TextFormatter;

// This code is based on code from https://edencoding.com/javafx-textfield/
public class NumberFormatterOperator extends BaseFormatterOperator {
    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        if (!change.getText().matches("\\d+")) {
            change.setText(""); //else make no change
            change.setRange(    //don't remove any selected text either.
                    change.getRangeStart(),
                    change.getRangeStart()
            );
        }
        return change; //if change is a number
    }
}
