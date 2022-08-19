package com.darrenfinch.appointmentmanager.data;

import com.darrenfinch.appointmentmanager.data.models.Appointment;
import com.darrenfinch.appointmentmanager.data.models.Customer;
import com.darrenfinch.appointmentmanager.data.models.User;

import java.util.List;

/**
 * This is a simple enough application that I did not see the need for separate DAOs for each entity in the database.
 * Instead, I am creating a single source of truth via this main repository.
 * It will be a testable, stateless abstraction over the persistence layer.
 * I will break the pattern of "no updating objects in the repository" because that just seems completely arbitrary.
 */
public interface MainRepository {
    //APPOINTMENTS
    List<Appointment> getAppointmentsForUserByMonth(User user);
    List<Appointment> getAppointmentsForUserByWeek(User user);

    void addAppointment(Appointment appointment);
    void updateAppointment(int appointmentId, Appointment newAppointment);
    void removeAppointment(int appointmentId);

    //CUSTOMERS
    List<Customer> getAllCustomers();

    void addCustomer(Customer customer);
    void updateCustomer(int customerId, Customer newCustomer);
    void removeCustomer(int customerId);

    //User
    void getUserByUserName(String userName);
}
