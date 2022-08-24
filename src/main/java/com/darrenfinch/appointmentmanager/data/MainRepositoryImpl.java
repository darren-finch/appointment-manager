package com.darrenfinch.appointmentmanager.data;

import com.darrenfinch.appointmentmanager.data.models.Appointment;
import com.darrenfinch.appointmentmanager.data.models.Customer;
import com.darrenfinch.appointmentmanager.data.models.User;

import java.util.List;

public class MainRepositoryImpl implements MainRepository {
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
        return null;
    }
}
