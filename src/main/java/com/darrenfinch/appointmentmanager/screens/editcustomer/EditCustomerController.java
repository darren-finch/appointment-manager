package com.darrenfinch.appointmentmanager.screens.editcustomer;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Country;
import com.darrenfinch.appointmentmanager.common.data.entities.FirstLevelDivision;
import com.darrenfinch.appointmentmanager.common.services.DialogManager;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.function.UnaryOperator;

// TODO: CHANGE BACKGROUND THREAD TASK RESPONSIBILITY FROM MAIN REPOSITORY TO CONTROLLER. USE TASKS INSTEAD OF NEW THREAD.
public class EditCustomerController {
    private final ScreenNavigator screenNavigator;

    private final DialogManager dialogManager;

    private final MainRepository mainRepository;

    private final EditCustomerModel model;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private ComboBox<FirstLevelDivision> divisionComboBox;

    @FXML
    private Label errorLabel;

    public EditCustomerController(ScreenNavigator screenNavigator, DialogManager dialogManager, MainRepository mainRepository, EditCustomerModel model) {
        this.screenNavigator = screenNavigator;
        this.dialogManager = dialogManager;
        this.mainRepository = mainRepository;
        this.model = model;
    }

    @FXML
    public void initialize() {
        // This code comes from https://edencoding.com/javafx-textfield/
        UnaryOperator<TextFormatter.Change> numberValidationFormatter = change -> {
            if (!change.getText().matches("\\d+")) {
                change.setText(""); //else make no change
                change.setRange(    //don't remove any selected text either.
                        change.getRangeStart(),
                        change.getRangeStart()
                );
            }
            return change; //if change is a number
        };

        idTextField.textProperty().bind(model.idProperty());

        nameTextField.textProperty().bindBidirectional(model.nameProperty());

        phoneNumberTextField.textProperty().bindBidirectional(model.phoneNumberProperty());
        phoneNumberTextField.setTextFormatter(new TextFormatter<>(numberValidationFormatter));

        addressTextField.textProperty().bindBidirectional(model.addressProperty());

        postalCodeTextField.textProperty().bindBidirectional(model.postalCodeProperty());
        postalCodeTextField.setTextFormatter(new TextFormatter<>(numberValidationFormatter));

        countryComboBox.setCellFactory(countryListView -> new CountryListCell());
        countryComboBox.setButtonCell(new CountryListCell());
        countryComboBox.itemsProperty().bind(model.allCountriesProperty());
        countryComboBox.valueProperty().bindBidirectional(model.countryProperty());
        countryComboBox.getSelectionModel().selectFirst();

        divisionComboBox.setCellFactory(firstLevelDivisionListView -> new FirstLevelDivisionListCell());
        divisionComboBox.setButtonCell(new FirstLevelDivisionListCell());
        divisionComboBox.itemsProperty().bind(model.allFirstLevelDivisionsForCountryProperty());
        divisionComboBox.valueProperty().bindBidirectional(model.firstLevelDivisionProperty());

        errorLabel.textProperty().bind(model.errorProperty());
    }

    public void goBack() {
        dialogManager.showConfirmationDialog("You will lose any unsaved data if you go back. Are you sure?", screenNavigator::goBack, null);
    }

    // Got these implementations from https://www.baeldung.com/javafx-listview-display-custom-items
    private static class CountryListCell extends ListCell<Country> {
        @Override
        protected void updateItem(Country country, boolean empty) {
            super.updateItem(country, empty);
            if (empty || country == null) {
                setText(null);
            } else {
                setText(country.getName());
            }
        }
    }
    private static class FirstLevelDivisionListCell extends ListCell<FirstLevelDivision> {
        @Override
        protected void updateItem(FirstLevelDivision firstLevelDivision, boolean empty) {
            super.updateItem(firstLevelDivision, empty);
            if (empty || firstLevelDivision == null) {
                setText(null);
            } else {
                setText(firstLevelDivision.getName());
            }
        }
    }
}
