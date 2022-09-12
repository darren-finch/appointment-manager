package com.darrenfinch.appointmentmanager.screens.reports;

import com.darrenfinch.appointmentmanager.common.data.MainRepository;
import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.services.ScreenNavigator;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.text.DateFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ReportsController {
    private final ScreenNavigator screenNavigator;
    private final ExecutorService executorService;
    private final MainRepository mainRepository;
    private final ReportsModel model;

    private final GetNumberOfCustomerAppointmentsByTypeAndMonthService getNumberOfCustomerAppointmentsByTypeAndMonthService;

    @FXML
    private ComboBox<String> appointmentTypeFilterComboBox;
    @FXML
    private ComboBox<String> appointmentMonthFilterComboBox;
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
        this.getNumberOfCustomerAppointmentsByTypeAndMonthService = new GetNumberOfCustomerAppointmentsByTypeAndMonthService();
        this.getNumberOfCustomerAppointmentsByTypeAndMonthService.setExecutor(this.executorService);
    }

    @FXML
    public void initialize() {
        setupUI();
        setupModelData();
    }

    private void setupUI() {
        appointmentTypeFilterComboBox.setItems(Appointment.TYPES);
        appointmentTypeFilterComboBox.valueProperty().bindBidirectional(model.appointmentTypeFilterProperty());

        // TODO: DateFormatSymbols.getInstance().getMonths() has an empty value at the end. Remove this.
        appointmentMonthFilterComboBox.setItems(FXCollections.observableList(List.of(DateFormatSymbols.getInstance().getMonths())));
        appointmentMonthFilterComboBox.valueProperty().bindBidirectional(model.appointmentMonthFilterProperty());

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
        getNumberOfCustomerAppointmentsByTypeAndMonthService.setOnSucceeded(workerStateEvent -> {
            model.setNumberOfCustomerAppointmentsByTypeAndMonth(getNumberOfCustomerAppointmentsByTypeAndMonthService.getValue());
        });

        model.appointmentTypeFilterProperty().addListener((obs, oldVal, newVal) -> {
            getNumberOfCustomerAppointmentsByTypeAndMonthService.restart();
        });
        model.appointmentMonthFilterProperty().addListener((obs, oldVal, newVal) -> {
            getNumberOfCustomerAppointmentsByTypeAndMonthService.restart();
        });

        getNumberOfCustomerAppointmentsByTypeAndMonthService.start();

        executorService.execute(new Task<>() {
            @Override
            protected Object call() throws Exception {
                model.setContactSchedules(mainRepository.getContactSchedules());
                model.setNumberOfCustomerAppointmentsByContact(mainRepository.getNumberOfAppointmentsByContact());
                return null;
            }
        });
    }

    public void back() {
        screenNavigator.goBack();
    }

    private class GetNumberOfCustomerAppointmentsByTypeAndMonthService extends Service<ObservableList<NumberOfCustomerAppointmentsForTypeAndMonth>> {
        @Override
        protected Task<ObservableList<NumberOfCustomerAppointmentsForTypeAndMonth>> createTask() {
            return new Task<>() {
                @Override
                protected ObservableList<NumberOfCustomerAppointmentsForTypeAndMonth> call() throws Exception {
                    return mainRepository.getNumberOfCustomerAppointmentsByTypeAndMonth(model.getAppointmentTypeFilter(), model.getAppointmentMonthFilter());
                }
            };
        }
    }
}