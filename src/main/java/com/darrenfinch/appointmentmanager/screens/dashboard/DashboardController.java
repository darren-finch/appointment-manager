package com.darrenfinch.appointmentmanager.screens.dashboard;

import com.darrenfinch.appointmentmanager.common.BaseController;
import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import com.darrenfinch.appointmentmanager.common.services.DialogManager;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.common.services.UserManager;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.Locale;

public class DashboardController implements BaseController {
    private final ScreenNavigator screenNavigator;
    private final DialogManager dialogManager;
    private final UserManager userManager;
    private final MainRepository mainRepository;
    private final DashboardModel model;
    @FXML
    private TableView<Customer> customersTableView;

    @FXML
    private ComboBox<String> viewByComboBox;

    public DashboardController(ScreenNavigator screenNavigator, DialogManager dialogManager, UserManager userManager, MainRepository mainRepository, DashboardModel model) {
        this.screenNavigator = screenNavigator;
        this.dialogManager = dialogManager;
        this.userManager = userManager;
        this.mainRepository = mainRepository;
        this.model = model;
    }

    @FXML
    public void initialize() {
        model.getCustomersProperty().set(mainRepository.getAllCustomers());
        customersTableView.itemsProperty().bind(model.getCustomersProperty());

        viewByComboBox.setItems(FXCollections.observableList(List.of("Week", "Month")));
        viewByComboBox.valueProperty().bindBidirectional(model.viewByProperty());

//        model.viewByProperty().set("Week");
//        model.viewByProperty().addListener((obs, oldVal, newVal) -> {
//            if (newVal.equalsIgnoreCase("week")) {
//                mainRepository.getAppointmentsForUserByWeek(userManager.getCurrentUser());
//            } else if (newVal.equalsIgnoreCase("month")){
//                mainRepository.getAppointmentsForUserByMonth(userManager.getCurrentUser());
//            }
//        });
    }
}