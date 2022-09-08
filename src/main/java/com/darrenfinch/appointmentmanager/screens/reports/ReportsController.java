package com.darrenfinch.appointmentmanager.screens.reports;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;

public class ReportsController {
    private final ScreenNavigator screenNavigator;
    private final ExecutorService executorService;
    private final MainRepository mainRepository;
    private final ReportsModel model;

    @FXML
    private TableView<NumberOfCustomerAppointmentsForTypeAndMonth> numberOfCustomerAppointmentsTableView;
    @FXML
    private TableView<ContactSchedule> contactSchedulesTableView;
    @FXML
    private TableView<NumberOfCustomerAppointmentsForContact> numberOfCustomerAppointmentsForContactTableView;

    public ReportsController(ScreenNavigator screenNavigator, ExecutorService executorService, MainRepository mainRepository, ReportsModel model) {
        this.screenNavigator = screenNavigator;
        this.executorService = executorService;
        this.mainRepository = mainRepository;
        this.model = model;
    }

    @FXML
    public void initialize() {
        setupUI();
        setupModelData();
    }

    private void setupUI() {
        numberOfCustomerAppointmentsTableView.itemsProperty().bind(model.numberOfCustomerAppointmentsByTypeAndMonthProperty());
        contactSchedulesTableView.itemsProperty().bind(model.contactSchedulesProperty());
        numberOfCustomerAppointmentsForContactTableView.itemsProperty().bind(model.numberOfCustomerAppointmentsByContactProperty());

        // Ensure date formatting is correct
        TableColumn<ContactSchedule, String> startDateTimeColumn = (TableColumn<ContactSchedule, String>) contactSchedulesTableView.getColumns().filtered(col -> col.getText().equals("Start Date/Time")).get(0);
        startDateTimeColumn.setCellValueFactory(appointmentStringCellDataFeatures -> {
            SimpleStringProperty strProp = new SimpleStringProperty();
            strProp.set(appointmentStringCellDataFeatures.getValue().getStartDateTime().format(DateTimeFormatter.ofPattern(Constants.STANDARD_DATE_TIME_FORMAT)));
            return strProp;
        });

        TableColumn<ContactSchedule, String> endDateTimeColumn = (TableColumn<ContactSchedule, String>) contactSchedulesTableView.getColumns().filtered(col -> col.getText().equals("End Date/Time")).get(0);
        endDateTimeColumn.setCellValueFactory(appointmentStringCellDataFeatures -> {
            SimpleStringProperty strProp = new SimpleStringProperty();
            strProp.set(appointmentStringCellDataFeatures.getValue().getEndDateTime().format(DateTimeFormatter.ofPattern(Constants.STANDARD_DATE_TIME_FORMAT)));
            return strProp;
        });
    }

    private void setupModelData() {
        executorService.execute(new Task<>() {
            @Override
            protected Object call() throws Exception {
                model.setNumberOfCustomerAppointmentsByTypeAndMonth(mainRepository.getNumberOfCustomerAppointmentsByTypeAndMonth());
                model.setContactSchedules(mainRepository.getContactSchedules());
                model.setNumberOfCustomerAppointmentsByContact(mainRepository.getNumberOfAppointmentsByContact());
                return null;
            }
        });
    }

    public void back() {
        screenNavigator.goBack();
    }
}