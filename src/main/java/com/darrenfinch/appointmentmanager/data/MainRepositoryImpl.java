package com.darrenfinch.appointmentmanager.data;

import com.darrenfinch.appointmentmanager.data.models.Appointment;
import com.darrenfinch.appointmentmanager.data.models.Customer;
import com.darrenfinch.appointmentmanager.data.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MainRepositoryImpl implements MainRepository {
    private final Connection dbConnection;

    public MainRepositoryImpl(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Appointment> getAppointmentsForUserByMonth(User user) {
        return null;
    }

    @Override
    public List<Appointment> getAppointmentsForUserByWeek(User user) {
        return null;
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
    public List<Customer> getAllCustomers() {
        return null;
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
        try (Statement statement = dbConnection.createStatement()) {
            String query = "SELECT * FROM users WHERE User_Name = \"" + userName + "\" LIMIT 1;";
            try (ResultSet resultSet = statement.executeQuery(query);) {
                //TODO: Vulnerable to SQL injection, use Prepared Statements
                // There should only be one result
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
}