package com.darrenfinch.appointmentmanager.common.data;

import com.darrenfinch.appointmentmanager.common.data.entities.*;
import com.darrenfinch.appointmentmanager.screens.dashboard.DashboardController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainRepositoryImpl implements MainRepository {
    private final Connection dbConnection;

    public MainRepositoryImpl(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Static data
    private final ObservableList<User> users = FXCollections.observableList(new ArrayList<>());
    private final ObservableList<Country> countries = FXCollections.observableList(new ArrayList<>());
    private final ObservableList<FirstLevelDivision> firstLevelDivisions = FXCollections.observableList(new ArrayList<>());
    private final ObservableList<Contact> contacts = FXCollections.observableList(new ArrayList<>());

    @Override
    public void initializeStaticData() {
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

        firstLevelDivisions.clear();
        String query2 = "SELECT * FROM first_level_divisions";
        try (Statement statement = dbConnection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query2)) {
                while (resultSet.next()) {
                    firstLevelDivisions.add(new FirstLevelDivision(
                            resultSet.getInt("Division_ID"),
                            resultSet.getString("Division"),
                            resultSet.getInt("Country_ID")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        contacts.clear();
        String query3 = "SELECT * FROM contacts";
        try (Statement statement = dbConnection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query3)) {
                while (resultSet.next()) {
                    contacts.add(new Contact(
                            resultSet.getInt("Contact_ID"),
                            resultSet.getString("Contact_Name"),
                            resultSet.getString("Email")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        users.clear();
        String query4 = "SELECT * FROM users";
        try (Statement statement = dbConnection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query4)) {
                while (resultSet.next()) {
                    users.add(new User(
                            resultSet.getInt("User_ID"),
                            resultSet.getString("User_Name"),
                            resultSet.getString("Password")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Appointment> getAppointmentsForUserByTimeFrame(int userId, DashboardController.ViewByTimeFrame viewByTimeFrame) {
        ObservableList<Appointment> appointments = FXCollections.observableList(new ArrayList<>());
        String query = "SELECT *, EXTRACT(" + viewByTimeFrame.name() + " FROM Start) as orderBy FROM appointments WHERE User_ID = ? ORDER BY orderBy DESC";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    appointments.add(new Appointment(
                            resultSet.getInt("Appointment_ID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Description"),
                            resultSet.getString("Location"),
                            resultSet.getString("Type"),
                            ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("start").replace(' ', 'T'), DateTimeFormatter.ISO_LOCAL_DATE_TIME), ZoneId.systemDefault()),
                            ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("start").replace(' ', 'T'), DateTimeFormatter.ISO_LOCAL_DATE_TIME), ZoneId.systemDefault()),
                            resultSet.getInt("Customer_ID"),
                            resultSet.getInt("User_ID"),
                            resultSet.getInt("Contact_ID")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public Appointment getAppointment(int appointmentId) {
        String query = "SELECT * from appointments WHERE Appointment_ID = ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, appointmentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Appointment(
                            resultSet.getInt("Appointment_ID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Description"),
                            resultSet.getString("Location"),
                            resultSet.getString("Type"),
                            ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("start").replace(' ', 'T'), DateTimeFormatter.ISO_LOCAL_DATE_TIME), ZoneId.systemDefault()),
                            ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("start").replace(' ', 'T'), DateTimeFormatter.ISO_LOCAL_DATE_TIME), ZoneId.systemDefault()),
                            resultSet.getInt("Customer_ID"),
                            resultSet.getInt("User_ID"),
                            resultSet.getInt("Contact_ID")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void addAppointment(Appointment appointment, User currentUser) {
        String query = "INSERT INTO appointments VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, appointment.getId());
            statement.setString(2, appointment.getTitle());
            statement.setString(3, appointment.getDescription());
            statement.setString(4, appointment.getLocation());
            statement.setString(5, appointment.getType());
            statement.setObject(6, Instant.from(appointment.getStartDateTime()));
            statement.setObject(7, Instant.from(appointment.getEndDateTime()));
            statement.setTimestamp(8, Timestamp.from(Instant.now()));
            statement.setString(9, currentUser.getName());
            statement.setTimestamp(10, Timestamp.from(Instant.now()));
            statement.setString(11, currentUser.getName());
            statement.setInt(12, appointment.getCustomerId());
            statement.setInt(13, appointment.getUserId());
            statement.setInt(14, appointment.getContactId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAppointment(int appointmentId, Appointment newAppointment, User currentUser) {
        String query = "UPDATE appointments " +
                "SET Title = ?, " +
                "Description = ?, " +
                "Location = ?, " +
                "Type = ?, " +
                "Start = ?, " +
                "End = ?, " +
                "Last_Update = ?, " +
                "Last_Updated_By = ?, " +
                "Customer_ID = ?, " +
                "User_ID = ?, " +
                "Contact_ID = ? " +
                "WHERE Appointment_ID = ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setString(1, newAppointment.getTitle());
            statement.setString(2, newAppointment.getDescription());
            statement.setString(3, newAppointment.getLocation());
            statement.setString(4, newAppointment.getType());
            statement.setObject(6, Instant.from(newAppointment.getStartDateTime()));
            statement.setObject(7, Instant.from(newAppointment.getEndDateTime()));
            statement.setTimestamp(7, Timestamp.from(Instant.now()));
            statement.setString(8, currentUser.getName());
            statement.setInt(9, newAppointment.getCustomerId());
            statement.setInt(10, newAppointment.getUserId());
            statement.setInt(11, newAppointment.getContactId());
            statement.setInt(12, newAppointment.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAppointment(int appointmentId) {
        String query = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, appointmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customers = FXCollections.observableList(new ArrayList<>());
        String query = "SELECT * FROM customers";
        try (Statement statement = dbConnection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    customers.add(new Customer(
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
        return customers;
    }

    @Override
    public Customer getCustomer(int customerId) {
        String query = "SELECT * from customers WHERE Customer_ID = ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Customer(
                            resultSet.getInt("Customer_ID"),
                            resultSet.getString("Customer_Name"),
                            resultSet.getString("Address"),
                            resultSet.getString("Postal_Code"),
                            resultSet.getString("Phone"),
                            resultSet.getInt("Division_ID")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void addCustomer(Customer customer, User currentUser) {
        String query = "INSERT INTO customers VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, customer.getId());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getPostalCode());
            statement.setString(5, customer.getPhoneNumber());
            statement.setObject(6, Instant.now());
            statement.setString(7, currentUser.getName());
            statement.setTimestamp(8, Timestamp.from(Instant.now()));
            statement.setString(9, currentUser.getName());
            statement.setInt(10, customer.getDivisionId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCustomer(int customerId, Customer newCustomer, User currentUser) {
        String query = "UPDATE customers " +
                "SET Customer_Name = ?, " +
                "Address = ?, " +
                "Postal_Code = ?, " +
                "Phone = ?, " +
                "Last_Update = ?, " +
                "Last_Updated_By = ?, " +
                "Division_ID = ? " +
                "WHERE Customer_ID = ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setString(1, newCustomer.getName());
            statement.setString(2, newCustomer.getAddress());
            statement.setString(3, newCustomer.getPostalCode());
            statement.setString(4, newCustomer.getPhoneNumber());
            statement.setTimestamp(5, Timestamp.from(Instant.now()));
            statement.setString(6, currentUser.getName());
            statement.setInt(7, newCustomer.getDivisionId());
            statement.setInt(8, newCustomer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCustomer(int customerId) {
        String query = "DELETE FROM customers WHERE Customer_ID = ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<User> getAllUsers() {
        return users;
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
        return FXCollections.observableList(firstLevelDivisions.stream().filter((val) -> val.getCountryId() == country.getId()).toList());
    }

    @Override
    public ObservableList<Contact> getAllContacts() {
        return contacts;
    }
}