package com.darrenfinch.appointmentmanager.screens.editcustomer;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Country;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import com.darrenfinch.appointmentmanager.common.data.entities.FirstLevelDivision;
import com.darrenfinch.appointmentmanager.common.services.DialogManager;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.common.services.UserManager;
import com.darrenfinch.appointmentmanager.common.ui.listcells.CountryListCell;
import com.darrenfinch.appointmentmanager.common.ui.listcells.FirstLevelDivisionListCell;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    /**
     * Constructs a new EditCustomerController with all the necessary dependencies.
     */
    public EditCustomerController(ScreenNavigator screenNavigator, DialogManager dialogManager, UserManager userManager, MainRepository mainRepository, ExecutorService executorService, EditCustomerModel model) {
        this.screenNavigator = screenNavigator;
        this.dialogManager = dialogManager;
        this.userManager = userManager;
        this.mainRepository = mainRepository;
        this.executorService = executorService;
        this.model = model;
    }

    /**
     * Sets up the initial data model, binds the view to the model, and starts any services that will fetch data from the database.
     *
     * Inside the implementation of this method, 4 inline lambdas are used to enhance readability and conciseness, since their parameters are self-explanatory.
     *
     * <ol>
     *     <li>One is used in a forEach function to loop through each country in the database.</li>
     *     <li>Another one is used to filter a list of first level divisions for a particular country down to the exact country
     *     the customer we've gotten from the database is in.</li>
     *     <li>Another one is used to create new CountryListCells for the country combo box dropdown.</li>
     *     <li>The final one is used to create new FirstLevelDivisionCells for the first level division combo box dropdown.</li>
     * </ol>
     *
     * Any other places a lambda could have been used, they were not used because the use of a lambda would have
     * caused potential confusion about the parameter types to the functional interface.
     */
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
        model.setAllCountries(mainRepository.getAllCountries());
        model.setSelectedCountry(model.getAllCountries().get(0));

        model.setAllFirstLevelDivisionsForCountry(mainRepository.getFirstLevelDivisionsForCountry(model.getSelectedCountry()));
        model.setSelectedFirstLevelDivision(model.getAllFirstLevelDivisionsForCountry().get(0));

        model.selectedCountryProperty().addListener(new ChangeListener<Country>() {
            @Override
            public void changed(ObservableValue<? extends Country> obs, Country oldVal, Country newVal) {
                model.setAllFirstLevelDivisionsForCountry(mainRepository.getFirstLevelDivisionsForCountry(model.getSelectedCountry()));
                model.setSelectedFirstLevelDivision(model.getAllFirstLevelDivisionsForCountry().get(0));
            }
        });

        if (isEditingExistingCustomer) {
            GetCustomerTask getCustomerTask = new GetCustomerTask(mainRepository, customerId);
            getCustomerTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent workerStateEvent) {
                    Customer customer = getCustomerTask.getValue();
                    model.initializeWithCustomer(customer);

                    List<Country> allCountries = mainRepository.getAllCountries();
                    allCountries.forEach(country -> {
                        ObservableList<FirstLevelDivision> firstLevelDivisions = mainRepository.getFirstLevelDivisionsForCountry(country);
                        Optional<FirstLevelDivision> optionalFirstLevelDivision = firstLevelDivisions
                                .stream()
                                .filter(firstLevelDivision -> firstLevelDivision.getId() == customer.getDivisionId())
                                .findFirst();
                        if (optionalFirstLevelDivision.isPresent()) {
                            model.setSelectedCountry(country);

                            FirstLevelDivision firstLevelDivision = optionalFirstLevelDivision.get();
                            model.setAllFirstLevelDivisionsForCountry(firstLevelDivisions);
                            model.setSelectedFirstLevelDivision(firstLevelDivision);
                        }
                    });

                    workerStateEvent.consume();
                }
            });
            getCustomerTask.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent workerStateEvent) {
                    model.setError("An error occurred when loading the customer data.");
                    workerStateEvent.consume();
                }
            });
            executorService.execute(getCustomerTask);
        }
    }

    private void setupUI() {
        idTextField.textProperty().bind(model.idProperty());

        nameTextField.textProperty().bindBidirectional(model.nameProperty());

        phoneNumberTextField.textProperty().bindBidirectional(model.phoneNumberProperty());

        addressTextField.textProperty().bindBidirectional(model.addressProperty());

        postalCodeTextField.textProperty().bindBidirectional(model.postalCodeProperty());

        countryComboBox.setCellFactory(countryListView -> new CountryListCell());
        countryComboBox.setButtonCell(new CountryListCell());
        countryComboBox.itemsProperty().bind(model.allCountriesProperty());
        countryComboBox.valueProperty().bindBidirectional(model.selectedCountryProperty());
        countryComboBox.getSelectionModel().selectFirst();

        divisionComboBox.setCellFactory(firstLevelDivisionListView -> new FirstLevelDivisionListCell());
        divisionComboBox.setButtonCell(new FirstLevelDivisionListCell());
        divisionComboBox.itemsProperty().bind(model.allFirstLevelDivisionsForCountryProperty());
        divisionComboBox.valueProperty().bindBidirectional(model.selectedFirstLevelDivisionProperty());

        errorLabel.textProperty().bind(model.errorProperty());
    }

    /**
     * Shows a confirmation dialog letting the user know that any unsaved changes will be lost. If the user presses okay, it navigates to the previous screen.
     */
    public void goBack() {
        dialogManager.showConfirmationDialog("You will lose any unsaved data if you go back. Are you sure?", screenNavigator::goBack, null);
    }

    /**
     * Calls into the data model to ensure all the user-entered data is valid.
     * If the data is valid, it either adds a new customer to the database if the customerId argument was invalid,
     * or it updates an existing customer if the customerId argument was valid.
     *
     * @see EditCustomerModel#isValid()  EditCustomerModel.isValid()
     *
     * This function uses a lambda to reduce down the list of errors into a single string with each error on its own line.
     * Once again, the readability of using the lambda is much higher than an entire functional interface.
     */
    public void save() {
        if (model.isValid()) {
            Customer customerFromModel = model.toCustomer();
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
        } else {
            model.setError(model.getInvalidReasons().stream().reduce((prev, curr) -> prev + "\n" + curr).get());
        }
    }

    /**
     * Gets a customer from the database. If the provided customerId is invalid it will throw an exception.
     */
    private static class GetCustomerTask extends Task<Customer> {
        private final MainRepository mainRepository;
        private final int customerId;

        public GetCustomerTask(MainRepository mainRepository, int customerId) {
            this.mainRepository = mainRepository;
            this.customerId = customerId;
        }

        @Override
        protected Customer call() throws Exception {
            if (customerId != Constants.INVALID_ID) {
                return mainRepository.getCustomer(customerId);
            } else {
                throw new RuntimeException("Invalid customer ID.");
            }
        }
    }
}
