package com.darrenfinch.appointmentmanager.screens.reports;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;

public class ReportsController {
    private final ScreenNavigator screenNavigator;
    private final ExecutorService executorService;
    private final MainRepository mainRepository;
    private final ReportsModel model;

    private final RefreshReportsService refreshReportsService;

    @FXML
    private ComboBox<String> appointmentTypeFilterComboBox;
    @FXML
    private ComboBox<String> appointmentMonthFilterComboBox1;
    @FXML
    private ComboBox<String> appointmentMonthFilterComboBox2;
    @FXML
    private TableView<NumberOfCustomerAppointmentsForTypeAndMonth> numberOfCustomerAppointmentsTableView;
    @FXML
    private TableView<ContactSchedule> contactSchedulesTableView;
    @FXML
    private TableView<NumberOfContactAppointmentsForMonth> numberOfCustomerAppointmentsForContactTableView;

    public ReportsController(ScreenNavigator screenNavigator, ExecutorService executorService, MainRepository mainRepository, ReportsModel model) {
        this.screenNavigator = screenNavigator;
        this.executorService = executorService;
        this.mainRepository = mainRepository;
        this.model = model;
        this.refreshReportsService = new RefreshReportsService();
        this.refreshReportsService.setExecutor(this.executorService);
    }

    @FXML
    public void initialize() {
        setupUI();
        setupModelData();
    }

    private void setupUI() {
        // Setup number of customer appointments by type and month report UI
        appointmentTypeFilterComboBox.setItems(Appointment.TYPES);
        appointmentTypeFilterComboBox.valueProperty().bindBidirectional(model.appointmentTypeFilterProperty());
        appointmentMonthFilterComboBox1.setItems(FXCollections.observableList(Constants.MONTHS));
        appointmentMonthFilterComboBox1.valueProperty().bindBidirectional(model.appointmentMonthFilter1Property());
        numberOfCustomerAppointmentsTableView.itemsProperty().bind(model.numberOfCustomerAppointmentsByTypeAndMonthProperty());

        // Setup contact schedule report UI
        contactSchedulesTableView.itemsProperty().bind(model.contactSchedulesProperty());

        // Setup number of contact appointments by month report UI
        appointmentMonthFilterComboBox2.setItems(FXCollections.observableList(Constants.MONTHS));
        appointmentMonthFilterComboBox2.valueProperty().bindBidirectional(model.appointmentMonthFilter2Property());
        numberOfCustomerAppointmentsForContactTableView.itemsProperty().bind(model.numberOfContactAppointmentsByMonthProperty());

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
        model.appointmentTypeFilterProperty().addListener((obs, oldVal, newVal) -> {
            refreshReportsService.restart();
        });
        model.appointmentMonthFilter1Property().addListener((obs, oldVal, newVal) -> {
            refreshReportsService.restart();
        });
        model.appointmentMonthFilter2Property().addListener((obs, oldVal, newVal) -> {
            refreshReportsService.restart();
        });
        refreshReportsService.start();
    }

    public void back() {
        screenNavigator.goBack();
    }

    private class RefreshReportsService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    ObservableList<NumberOfCustomerAppointmentsForTypeAndMonth> report1Data = mainRepository.getNumberOfCustomerAppointmentsByTypeAndMonth(model.getAppointmentTypeFilter(), model.getAppointmentMonthFilter1());
                    ObservableList<ContactSchedule> report2Data = mainRepository.getContactSchedules();
                    ObservableList<NumberOfContactAppointmentsForMonth> report3Data = mainRepository.getNumberOfContactAppointmentsForMonth(model.getAppointmentMonthFilter2());

                    Platform.runLater(() -> {
                        model.setNumberOfCustomerAppointmentsByTypeAndMonth(report1Data);
                        model.setContactSchedules(report2Data);
                        model.setNumberOfContactAppointmentsByMonth(report3Data);
                    });

                    return null;
                }
            };
        }
    }
}