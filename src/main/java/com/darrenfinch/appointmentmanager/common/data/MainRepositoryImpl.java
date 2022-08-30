package com.darrenfinch.appointmentmanager.common.data;

import com.darrenfinch.appointmentmanager.common.data.entities.*;
import com.darrenfinch.appointmentmanager.screens.dashboard.DashboardController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainRepositoryImpl implements MainRepository {
    private final Connection dbConnection;

    public MainRepositoryImpl(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    private final ObservableList<Customer> customersObservableList = FXCollections.observableList(new ArrayList<>());
    private final ObservableList<Appointment> appointmentsObservableList = FXCollections.observableList(new ArrayList<>());

    private final ObservableList<Country> countries = FXCollections.observableList(new ArrayList<>());

    private final ObservableList<FirstLevelDivision> allFirstLevelDivisions = FXCollections.observableList(new ArrayList<>());

    @Override
    public void initializeStaticData() {
        // Get countries and first level division data from database. This is done synchronously to guarantee the availability of all static data to the application.
        countries.clear();
        String query1 = "SELECT * FROM countries";
        try (Statement statement = dbConnection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query1)) {
                while (resultSet.next()) {
                    countries.add(new Country(
                            resultSet.getInt("Country_ID"),
                            resultSet.getString("Country")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        allFirstLevelDivisions.clear();
        String query2 = "SELECT * FROM first_level_divisions";
        try (Statement statement = dbConnection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query2)) {
                while (resultSet.next()) {
                    allFirstLevelDivisions.add(new FirstLevelDivision(
                            resultSet.getInt("Division_ID"),
                            resultSet.getString("Division"),
                            resultSet.getInt("Country_ID")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Appointment> getAppointmentsForUserByTimeFrame(int userId, DashboardController.ViewByTimeFrame viewByTimeFrame) {
        new Thread(() -> {
            appointmentsObservableList.clear();
            String query = "SELECT *, EXTRACT(" + viewByTimeFrame.name() + " FROM Start) as orderBy FROM appointments WHERE User_ID = ? ORDER BY orderBy DESC";
            try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
                statement.setInt(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        appointmentsObservableList.add(new Appointment(
                                resultSet.getInt("Appointment_ID"),
                                resultSet.getString("Title"),
                                resultSet.getString("Description"),
                                resultSet.getString("Location"),
                                resultSet.getString("Type"),
                                LocalDate.parse(resultSet.getString("start").replace(' ', 'T'), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                LocalDate.parse(resultSet.getString("end").replace(' ', 'T'), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                                resultSet.getInt("Customer_ID"),
                                resultSet.getInt("User_ID"),
                                resultSet.getInt("Contact_ID")
                        ));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
        return appointmentsObservableList;
    }

    @Override
    public void addAppointment(Appointment appointment) {

    }

    @Override
    public void updateAppointment(int appointmentId, Appointment newAppointment) {

    }

    @Override
    public void removeAppointment(int appointmentId) {

    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        new Thread(() -> {
            customersObservableList.clear();
            String query = "SELECT * FROM customers";
            try (Statement statement = dbConnection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()) {
                        customersObservableList.add(new Customer(
                                resultSet.getInt("Customer_ID"),
                                resultSet.getString("Customer_Name"),
                                resultSet.getString("Address"),
                                resultSet.getString("Postal_Code"),
                                resultSet.getString("Phone"),
                                resultSet.getInt("Division_ID")
                        ));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
        return customersObservableList;
    }

    @Override
    public void addCustomer(Customer customer) {

    }

    @Override
    public void updateCustomer(int customerId, Customer newCustomer) {

    }

    @Override
    public void removeCustomer(int customerId) {

    }

    @Override
    public User getUserByUserName(String userName) {
        String query = "SELECT * FROM users WHERE User_Name = ? LIMIT 1;";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setString(1, userName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("User_ID"),
                            resultSet.getString("User_Name"),
                            resultSet.getString("Password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ObservableList<Country> getAllCountries() {
        return FXCollections.observableList(countries);
    }

    @Override
    public ObservableList<FirstLevelDivision> getFirstLevelDivisionsForCountry(Country country) {
        return FXCollections.observableList(allFirstLevelDivisions.stream().filter((val) -> val.getCountryId() == country.getId()).toList());
    }
}