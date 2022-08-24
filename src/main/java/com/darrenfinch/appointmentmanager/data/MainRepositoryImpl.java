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
        try {
            //TODO: Vulnerable to SQL injection, use Prepared Statements
            String query = "SELECT * FROM users WHERE username =" + userName;
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            // There should only be one result
            resultSet.next();
            return new User(
                    resultSet.getInt("user_id"),
                    resultSet.getString("user_name"),
                    resultSet.getString("user_password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
