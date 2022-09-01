package com.darrenfinch.appointmentmanager.screens.editcustomer;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Country;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import com.darrenfinch.appointmentmanager.common.data.entities.FirstLevelDivision;
import com.darrenfinch.appointmentmanager.common.services.DialogManager;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.common.services.UserManager;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import com.darrenfinch.appointmentmanager.common.utils.formatters.PhoneNumberFormatterOperator;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

public class EditCustomerController {
    private final ScreenNavigator screenNavigator;

    private final DialogManager dialogManager;

    private final UserManager userManager;

    private final MainRepository mainRepository;

    private final EditCustomerModel model;

    private final ExecutorService executorService;

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

    private int customerId = Constants.INVALID_ID;

    private boolean isEditingExistingCustomer = false;

    public EditCustomerController(ScreenNavigator screenNavigator, DialogManager dialogManager, UserManager userManager, MainRepository mainRepository, ExecutorService executorService, EditCustomerModel model) {
        this.screenNavigator = screenNavigator;
        this.dialogManager = dialogManager;
        this.userManager = userManager;
        this.mainRepository = mainRepository;
        this.executorService = executorService;
        this.model = model;
    }

    @FXML
    public void initialize() {
        setupArguments();
        setupModelData();
        setupUI();
    }

    private void setupArguments() {
        customerId = (int) screenNavigator.getArgument("customerId");
        isEditingExistingCustomer = customerId > Constants.INVALID_ID;
    }

    private void setupModelData() {
        model.allCountriesProperty().set(mainRepository.getAllCountries());
        model.countryProperty().set(model.getAllCountries().get(0));

        model.allFirstLevelDivisionsForCountryProperty().set(mainRepository.getFirstLevelDivisionsForCountry(model.getCountry()));
        model.firstLevelDivisionProperty().set(model.getAllFirstLevelDivisionsForCountry().get(0));

        model.countryProperty().addListener((obs, oldVal, newVal) -> {
            model.allFirstLevelDivisionsForCountryProperty().set(mainRepository.getFirstLevelDivisionsForCountry(model.getCountry()));
            model.firstLevelDivisionProperty().set(model.getAllFirstLevelDivisionsForCountry().get(0));
        });

        if (isEditingExistingCustomer) {
            GetCustomerTask getCustomerTask = new GetCustomerTask(mainRepository, customerId);
            getCustomerTask.setOnSucceeded(workerStateEvent -> {
                Customer customer = getCustomerTask.getValue();
                model.idProperty().set(String.valueOf(customer.getId()));
                model.nameProperty().set(customer.getName());
                model.phoneNumberProperty().set(customer.getPhoneNumber());
                model.addressProperty().set(customer.getAddress());
                model.postalCodeProperty().set(customer.getPostalCode());

                List<Country> allCountries = mainRepository.getAllCountries();
                allCountries.forEach(country -> {
                    ObservableList<FirstLevelDivision> firstLevelDivisions = mainRepository.getFirstLevelDivisionsForCountry(country);
                    Optional<FirstLevelDivision> optionalFirstLevelDivision = firstLevelDivisions
                            .stream()
                            .filter(firstLevelDivision -> firstLevelDivision.getId() == customer.getDivisionId())
                            .findFirst();
                    if (optionalFirstLevelDivision.isPresent()) {
                        model.countryProperty().set(country);

                        FirstLevelDivision firstLevelDivision = optionalFirstLevelDivision.get();
                        model.allFirstLevelDivisionsForCountryProperty().set(firstLevelDivisions);
                        model.firstLevelDivisionProperty().set(firstLevelDivision);
                    }
                });

                workerStateEvent.consume();
            });
            getCustomerTask.setOnFailed(workerStateEvent -> {
                model.errorProperty().set("An error occurred when loading the customer data.");
            });
            executorService.execute(getCustomerTask);
        }
    }

    private void setupUI() {
        idTextField.textProperty().bind(model.idProperty());

        nameTextField.textProperty().bindBidirectional(model.nameProperty());

        phoneNumberTextField.textProperty().bindBidirectional(model.phoneNumberProperty());
        // TODO: IMPLEMENT FORMATTER
//        phoneNumberTextField.setTextFormatter(new TextFormatter<>(new PhoneNumberFormatterOperator()));

        addressTextField.textProperty().bindBidirectional(model.addressProperty());

        postalCodeTextField.textProperty().bindBidirectional(model.postalCodeProperty());

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

    public void save() {
        Customer customerFromModel = new Customer(
                Integer.parseInt(model.getId()),
                model.getName(),
                model.getAddress(),
                model.getPostalCode(),
                model.getPhoneNumber(),
                model.getFirstLevelDivision().getId()
        );
        executorService.execute(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                if (isEditingExistingCustomer) {
                    mainRepository.updateCustomer(customerId, customerFromModel, userManager.getCurrentUser());
                } else {
                    mainRepository.addCustomer(customerFromModel, userManager.getCurrentUser());
                }
                return null;
            }
        });
        screenNavigator.switchToDashboardScreen();
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
