package com.darrenfinch.appointmentmanager.screens.reports;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.data.entities.Contact;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.common.ui.listcells.ContactListCell;
import com.darrenfinch.appointmentmanager.common.ui.listcells.TimeFilterListCell;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import com.darrenfinch.appointmentmanager.common.utils.TimeFilter;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

public class ReportsController {
    private final ScreenNavigator screenNavigator;
    private final ExecutorService executorService;
    private final MainRepository mainRepository;
    private final ReportsModel model;

    private final RefreshReportsService refreshReportsService;

    @FXML
    private ComboBox<String> report1TypeComboBox;
    @FXML
    private ComboBox<String> report2MonthComboBox;
    @FXML
    private ComboBox<Contact> report3ContactComboBox;
    @FXML
    private ComboBox<TimeFilter> report4TimeFilterComboBox;
    @FXML
    private TableView<NumberOfAppointmentsForCustomer> report1TableView;
    @FXML
    private TableView<NumberOfAppointmentsForCustomer> report2TableView;
    @FXML
    private TableView<ContactAppointment> report3TableView;
    @FXML
    private TableView<NumberOfAppointmentsForContact> report4TableView;

    private boolean firstLoad = true;

    public ReportsController(ScreenNavigator screenNavigator, ExecutorService executorService, MainRepository mainRepository, ReportsModel model) {
        this.screenNavigator = screenNavigator;
        this.executorService = executorService;
        this.mainRepository = mainRepository;
        this.model = model;
        this.refreshReportsService = new RefreshReportsService();
        this.refreshReportsService.setExecutor(this.executorService);
        this.refreshReportsService.setOnFailed(workerStateEvent -> {
            workerStateEvent.getSource().getException().printStackTrace();
        });
    }

    @FXML
    public void initialize() {
        setupModelData();
        setupUI();

        refreshReportsService.start();
    }

    private void setupUI() {
        // Setup number of customer appointments by type UI
        report1TypeComboBox.setItems(Appointment.TYPES);
        report1TypeComboBox.valueProperty().bindBidirectional(model.report1TypeFilterProperty());
        report1TableView.itemsProperty().bind(model.report1DataProperty());

        // Setup number of customer appointments by month UI
        report2MonthComboBox.setItems(Constants.MONTHS);
        report2MonthComboBox.valueProperty().bindBidirectional(model.report2MonthFilterProperty());
        report2TableView.itemsProperty().bind(model.report2DataProperty());

        // Setup contact schedule UI
        report3ContactComboBox.setItems(model.getAllContacts());
        report3ContactComboBox.valueProperty().bindBidirectional(model.report3ContactFilterProperty());
        report3ContactComboBox.setCellFactory(contactListView -> new ContactListCell());
        report3ContactComboBox.setButtonCell(new ContactListCell());
        report3TableView.itemsProperty().bind(model.report3DataProperty());

        // Setup number of contact appointments by week, month, or all-time UI
        report4TimeFilterComboBox.setItems(FXCollections.observableList(Arrays.asList(TimeFilter.values())));
        report4TimeFilterComboBox.valueProperty().bindBidirectional(model.report4TimeFilterProperty());
        report4TimeFilterComboBox.setCellFactory(timeFilterListView -> new TimeFilterListCell());
        report4TimeFilterComboBox.setButtonCell(new TimeFilterListCell());
        report4TableView.itemsProperty().bind(model.report4DataProperty());

        // Ensure date formatting is correct
        TableColumn<ContactAppointment, String> startDateTimeColumn = (TableColumn<ContactAppointment, String>) report3TableView.getColumns().filtered(col -> col.getText().equals("Start Date/Time")).get(0);
        startDateTimeColumn.setCellValueFactory(appointmentStringCellDataFeatures -> {
            SimpleStringProperty strProp = new SimpleStringProperty();
            strProp.set(appointmentStringCellDataFeatures.getValue().getStartDateTime().format(DateTimeFormatter.ofPattern(Constants.STANDARD_DATE_TIME_FORMAT)));
            return strProp;
        });

        TableColumn<ContactAppointment, String> endDateTimeColumn = (TableColumn<ContactAppointment, String>) report3TableView.getColumns().filtered(col -> col.getText().equals("End Date/Time")).get(0);
        endDateTimeColumn.setCellValueFactory(appointmentStringCellDataFeatures -> {
            SimpleStringProperty strProp = new SimpleStringProperty();
            strProp.set(appointmentStringCellDataFeatures.getValue().getEndDateTime().format(DateTimeFormatter.ofPattern(Constants.STANDARD_DATE_TIME_FORMAT)));
            return strProp;
        });
    }

    private void setupModelData() {
        model.initialize(mainRepository.getAllContacts());

        report1TypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!firstLoad)
                refreshReportsService.restart();
        });
        report2MonthComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!firstLoad)
                refreshReportsService.restart();
        });
        report3ContactComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!firstLoad)
                refreshReportsService.restart();
        });
        report4TimeFilterComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!firstLoad)
                refreshReportsService.restart();
        });
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
                    ObservableList<NumberOfAppointmentsForCustomer> report1Data = mainRepository.getNumberOfCustomerAppointmentsForType(model.getReport1TypeFilter());
                    ObservableList<NumberOfAppointmentsForCustomer> report2Data = mainRepository.getNumberOfCustomerAppointmentsForMonth(model.getReport2MonthFilter());
                    ObservableList<ContactAppointment> report3Data = mainRepository.getScheduleForContact(model.getReport3ContactFilter().getId());
                    ObservableList<NumberOfAppointmentsForContact> report4Data = mainRepository.getNumberOfAppointmentsForContactAndTimeFilter(model.getReport4TimeFilter());

                    Platform.runLater(() -> {
                        model.setReport1Data(report1Data);
                        model.setReport2Data(report2Data);
                        model.setReport3Data(report3Data);
                        model.setReport4Data(report4Data);
                        firstLoad = false;
                    });

                    return null;
                }
            };
        }
    }
}