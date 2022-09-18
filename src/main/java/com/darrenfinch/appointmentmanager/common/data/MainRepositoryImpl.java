package com.darrenfinch.appointmentmanager.common.data;

import com.darrenfinch.appointmentmanager.common.data.entities.*;
import com.darrenfinch.appointmentmanager.common.data.misc.TimeFilter;
import com.darrenfinch.appointmentmanager.common.exceptions.UserNotFoundException;
import com.darrenfinch.appointmentmanager.common.services.TimeHelper;
import com.darrenfinch.appointmentmanager.common.utils.Constants;
import com.darrenfinch.appointmentmanager.common.exceptions.CustomerHasAppointmentsException;
import com.darrenfinch.appointmentmanager.screens.dashboard.CustomerWithLocationData;
import com.darrenfinch.appointmentmanager.screens.reports.ContactAppointment;
import com.darrenfinch.appointmentmanager.screens.reports.NumberOfAppointmentsForContact;
import com.darrenfinch.appointmentmanager.screens.reports.NumberOfAppointmentsForCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class MainRepositoryImpl implements MainRepository {
    private final Connection dbConnection;
    private final TimeHelper timeHelper;

    public MainRepositoryImpl(Connection dbConnection, TimeHelper timeHelper) {
        this.dbConnection = dbConnection;
        this.timeHelper = timeHelper;
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
            ResultSet resultSet = statement.executeQuery(query1);
            while (resultSet.next()) {
                countries.add(new Country(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                ));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        firstLevelDivisions.clear();
        String query2 = "SELECT * FROM first_level_divisions";
        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query2);
            while (resultSet.next()) {
                firstLevelDivisions.add(new FirstLevelDivision(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("Country_ID")
                ));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        contacts.clear();
        String query3 = "SELECT * FROM contacts";
        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query3);
            while (resultSet.next()) {
                contacts.add(new Contact(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email")
                ));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        users.clear();
        String query4 = "SELECT * FROM users";
        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query4);
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("User_ID"),
                        resultSet.getString("User_Name"),
                        resultSet.getString("Password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Appointment> getUpcomingAppointmentsForUser(int userId) {
        ObservableList<Appointment> appointments = FXCollections.observableList(new ArrayList<>());
        String query = "SELECT a.*, c.Contact_Name from appointments a INNER JOIN contacts c ON a.Contact_ID = c.Contact_ID WHERE User_ID = ? AND Start >= ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setObject(2, timeHelper.systemTimeNow());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                appointments.add(getAppointmentFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public ObservableList<Appointment> getAppointmentsByTimeFilter(TimeFilter timeFilter) {
        ObservableList<Appointment> appointments = FXCollections.observableList(new ArrayList<>());

        if (timeFilter == TimeFilter.WEEK) {
            String query = "SELECT a.*, c.Contact_Name from appointments a INNER JOIN contacts c ON a.Contact_ID = c.Contact_ID WHERE WEEK(CONVERT_TZ(Start, \"" + Constants.DATABASE_TIME_ZONE_OFFSET + "\", ?)) = ?";
            try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
                statement.setString(1, timeHelper.systemTimeNow().format(DateTimeFormatter.ofPattern("xxx")));
                statement.setInt(2, timeHelper.systemTimeNow().get(ChronoField.ALIGNED_WEEK_OF_YEAR));
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    appointments.add(getAppointmentFromResultSet(resultSet));
                }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (timeFilter == TimeFilter.MONTH) {
            String query = "SELECT a.*, c.Contact_Name from appointments a INNER JOIN contacts c ON a.Contact_ID = c.Contact_ID WHERE MONTH(CONVERT_TZ(Start, \"" + Constants.DATABASE_TIME_ZONE_OFFSET + "\", ?)) = ?";
            try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
                statement.setString(1, timeHelper.systemTimeNow().format(DateTimeFormatter.ofPattern("xxx")));
                statement.setInt(2, timeHelper.systemTimeNow().get(ChronoField.MONTH_OF_YEAR));
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    appointments.add(getAppointmentFromResultSet(resultSet));
                }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String query = "SELECT a.*, c.Contact_Name from appointments a INNER JOIN contacts c ON a.Contact_ID = c.Contact_ID";
            try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    appointments.add(getAppointmentFromResultSet(resultSet));
                }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return appointments;
    }

    @Override
    public ObservableList<Appointment> getAppointmentsForContact(int contactId) {
        ObservableList<Appointment> appointments = FXCollections.observableList(new ArrayList<>());
        String query = "SELECT a.*, c.Contact_Name from appointments a INNER JOIN contacts c ON a.Contact_ID = c.Contact_ID WHERE c.Contact_ID = ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, contactId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                appointments.add(getAppointmentFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public ObservableList<Appointment> getAppointmentsForCustomer(int customerId) {
        ObservableList<Appointment> appointments = FXCollections.observableList(new ArrayList<>());
        String query = "SELECT a.*, c.Contact_Name from appointments a INNER JOIN contacts c ON a.Contact_ID = c.Contact_ID WHERE Customer_ID = ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                appointments.add(getAppointmentFromResultSet(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public Appointment getAppointment(int appointmentId) {
        String query = "SELECT a.*, c.Contact_Name from appointments a INNER JOIN contacts c ON a.Contact_ID = c.Contact_ID WHERE Appointment_ID = ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, appointmentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getAppointmentFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Appointment getAppointmentFromResultSet(ResultSet resultSet) throws SQLException {
        return new Appointment(
                resultSet.getInt("Appointment_ID"),
                resultSet.getString("Title"),
                resultSet.getString("Description"),
                resultSet.getString("Location"),
                resultSet.getString("Type"),
                resultSet.getString("Contact_Name"),
                resultSet.getTimestamp("Start").toInstant().atZone(timeHelper.defaultZone()),
                resultSet.getTimestamp("End").toInstant().atZone(timeHelper.defaultZone()),
                resultSet.getInt("Customer_ID"),
                resultSet.getInt("User_ID"),
                resultSet.getInt("Contact_ID")
        );
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
            statement.setTimestamp(8, Timestamp.from(timeHelper.nowAsInstant()));
            statement.setString(9, currentUser.getName());
            statement.setTimestamp(10, Timestamp.from(timeHelper.nowAsInstant()));
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
            statement.setObject(5, newAppointment.getStartDateTime());
            statement.setObject(6, newAppointment.getEndDateTime());
            statement.setTimestamp(7, Timestamp.from(timeHelper.nowAsInstant()));
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
    public ObservableList<CustomerWithLocationData> getAllCustomersWithLocationData() {
        ObservableList<CustomerWithLocationData> customers = FXCollections.observableList(new ArrayList<>());
        String query = "SELECT * from customers cu INNER JOIN first_level_divisions f ON cu.Division_ID = f.Division_ID INNER JOIN countries co ON co.Country_ID = f.Country_ID";
        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                customers.add(new CustomerWithLocationData(
                        new Customer(
                                resultSet.getInt("Customer_ID"),
                                resultSet.getString("Customer_Name"),
                                resultSet.getString("Address"),
                                resultSet.getString("Postal_Code"),
                                resultSet.getString("Phone"),
                                resultSet.getInt("Division_ID")
                        ),
                        resultSet.getString("Division"),
                        resultSet.getString("Country")
                ));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customers = FXCollections.observableList(new ArrayList<>());
        String query = "SELECT * FROM customers";
        try (Statement statement = dbConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                customers.add(getCustomerFromResultSet(resultSet));
            }
            resultSet.close();
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
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getCustomerFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Customer getCustomerFromResultSet(ResultSet resultSet) throws SQLException {
        return new Customer(
                resultSet.getInt("Customer_ID"),
                resultSet.getString("Customer_Name"),
                resultSet.getString("Address"),
                resultSet.getString("Postal_Code"),
                resultSet.getString("Phone"),
                resultSet.getInt("Division_ID")
        );
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
            statement.setObject(6, timeHelper.nowAsInstant());
            statement.setString(7, currentUser.getName());
            statement.setTimestamp(8, Timestamp.from(timeHelper.nowAsInstant()));
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
            statement.setTimestamp(5, Timestamp.from(timeHelper.nowAsInstant()));
            statement.setString(6, currentUser.getName());
            statement.setInt(7, newCustomer.getDivisionId());
            statement.setInt(8, newCustomer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCustomer(int customerId) throws CustomerHasAppointmentsException {
        ObservableList<Appointment> appointmentsForCustomer = getAppointmentsForCustomer(customerId);
        if (!appointmentsForCustomer.isEmpty())
            throw new CustomerHasAppointmentsException(customerId);

        String query1 = "DELETE FROM customers WHERE Customer_ID = ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query1)) {
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
    public User getUserByUserName(String userName) throws UserNotFoundException {
        List<User> userList = users.filtered(user -> user.getName().equals(userName));
        if (userList.isEmpty()) {
            throw new UserNotFoundException();
        }
        return userList.get(0);
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

    @Override
    public ObservableList<NumberOfAppointmentsForCustomer> getNumberOfCustomerAppointmentsForType(String type) {
        ObservableList<NumberOfAppointmentsForCustomer> numberOfAppointmentsForCustomersByType = FXCollections.observableList(new ArrayList<>());
        String query = "SELECT c.Customer_ID, c.Customer_Name, COUNT(*) as Number_of_Appointments" +
                " FROM appointments a INNER JOIN customers c ON a.Customer_ID = c.Customer_ID" +
                " WHERE LCASE(a.Type) LIKE LCASE(?) GROUP BY c.Customer_ID, c.Customer_Name";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setString(1, type);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                numberOfAppointmentsForCustomersByType.add(
                        new NumberOfAppointmentsForCustomer(
                                resultSet.getInt("Customer_ID"),
                                resultSet.getString("Customer_Name"),
                                resultSet.getInt("Number_Of_Appointments")
                        )
                );
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfAppointmentsForCustomersByType;
    }

    @Override
    public ObservableList<NumberOfAppointmentsForCustomer> getNumberOfCustomerAppointmentsForMonthThisYear(String month) {
        ObservableList<NumberOfAppointmentsForCustomer> numberOfAppointmentsForCustomersByMonth = FXCollections.observableList(new ArrayList<>());
        String query = "SELECT c.Customer_ID, c.Customer_Name, COUNT(*) as Number_of_Appointments" +
                " FROM appointments a INNER JOIN customers c ON a.Customer_ID = c.Customer_ID" +
                " WHERE LCASE(MONTHNAME(a.Start)) LIKE LCASE(?) AND YEAR(CONVERT_TZ(a.Start, ?, ?)) = ? GROUP BY c.Customer_ID, c.Customer_Name";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setString(1, month);
            statement.setString(2, Constants.DATABASE_TIME_ZONE_OFFSET);
            statement.setString(3, timeHelper.systemTimeNow().format(DateTimeFormatter.ofPattern("xxx")));
            statement.setInt(4, timeHelper.systemTimeNow().get(ChronoField.YEAR));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                numberOfAppointmentsForCustomersByMonth.add(
                        new NumberOfAppointmentsForCustomer(
                                resultSet.getInt("Customer_ID"),
                                resultSet.getString("Customer_Name"),
                                resultSet.getInt("Number_Of_Appointments")
                        )
                );
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfAppointmentsForCustomersByMonth;
    }

    @Override
    public ObservableList<ContactAppointment> getUpcomingScheduleForContact(int contactId) {
        ObservableList<ContactAppointment> contactAppointments = FXCollections.observableList(new ArrayList<>());
        String query = "SELECT * FROM contacts c INNER JOIN appointments a ON c.Contact_ID = a.Contact_ID WHERE a.Contact_ID = ? AND a.Start > ?";
        try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
            statement.setInt(1, contactId);
            statement.setObject(2, timeHelper.nowAsInstant());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                contactAppointments.add(
                        new ContactAppointment(
                                resultSet.getString("Contact_Name"),
                                resultSet.getString("Email"),
                                resultSet.getInt("Appointment_ID"),
                                resultSet.getString("Title"),
                                resultSet.getString("Type"),
                                resultSet.getString("Description"),
                                resultSet.getTimestamp("Start").toInstant().atZone(timeHelper.defaultZone()),
                                resultSet.getTimestamp("End").toInstant().atZone(timeHelper.defaultZone()),
                                resultSet.getInt("Customer_ID")
                        )
                );
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactAppointments;
    }

    @Override
    public ObservableList<NumberOfAppointmentsForContact> getNumberOfAppointmentsForContactAndTimeFilter(TimeFilter timeFilter) {
        ObservableList<NumberOfAppointmentsForContact> numberOfCustomerAppointmentsByContact = FXCollections.observableList(new ArrayList<>());

        if (timeFilter == com.darrenfinch.appointmentmanager.common.data.misc.TimeFilter.WEEK) {
            String query = "SELECT c.Contact_Name, c.Email, COUNT(a.Appointment_ID) AS Number_Of_Appointments FROM appointments" +
                    " a INNER JOIN contacts c ON a.Contact_ID = c.Contact_ID WHERE WEEK(CONVERT_TZ(a.Start, ?, ?)) = ? GROUP BY c.Contact_Name, c.Email, c.Contact_ID";
            try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
                statement.setString(1, Constants.DATABASE_TIME_ZONE_OFFSET);
                statement.setString(2, timeHelper.systemTimeNow().format(DateTimeFormatter.ofPattern("xxx")));
                statement.setInt(3, timeHelper.systemTimeNow().get(ChronoField.ALIGNED_WEEK_OF_YEAR));
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    numberOfCustomerAppointmentsByContact.add(
                            getNumberOfAppointmentsForContactFromResultSet(resultSet)
                    );
                }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (timeFilter == com.darrenfinch.appointmentmanager.common.data.misc.TimeFilter.MONTH) {
            String query = "SELECT c.Contact_Name, c.Email, COUNT(a.Appointment_ID) AS Number_Of_Appointments FROM appointments" +
                    " a INNER JOIN contacts c ON a.Contact_ID = c.Contact_ID WHERE MONTH(CONVERT_TZ(a.Start, ?, ?)) = ? GROUP BY c.Contact_Name, c.Email, c.Contact_ID";
            try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
                statement.setString(1, Constants.DATABASE_TIME_ZONE_OFFSET);
                statement.setString(2, timeHelper.systemTimeNow().format(DateTimeFormatter.ofPattern("xxx")));
                statement.setInt(3, timeHelper.systemTimeNow().get(ChronoField.MONTH_OF_YEAR));
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    numberOfCustomerAppointmentsByContact.add(
                            getNumberOfAppointmentsForContactFromResultSet(resultSet)
                    );
                }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String query = "SELECT c.Contact_Name, c.Email, COUNT(c.Contact_ID) as Number_Of_Appointments FROM appointments a" +
                    " INNER JOIN contacts c ON a.Contact_ID = c.Contact_ID GROUP BY c.Contact_Name, c.Email, c.Contact_ID";
            try (PreparedStatement statement = dbConnection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    numberOfCustomerAppointmentsByContact.add(
                            getNumberOfAppointmentsForContactFromResultSet(resultSet)
                    );
                }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return numberOfCustomerAppointmentsByContact;
    }

    private NumberOfAppointmentsForContact getNumberOfAppointmentsForContactFromResultSet(ResultSet resultSet) throws SQLException {
        return new NumberOfAppointmentsForContact(
                resultSet.getString("Contact_Name"),
                resultSet.getString("Email"),
                resultSet.getInt("Number_Of_Appointments")
        );
    }
}