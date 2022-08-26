package com.darrenfinch.appointmentmanager.common.data;

import com.darrenfinch.appointmentmanager.common.data.entities.Appointment;
import com.darrenfinch.appointmentmanager.common.data.entities.Customer;
import com.darrenfinch.appointmentmanager.common.data.entities.User;
import javafx.collections.ObservableList;

/**
 * This is a simple enough application that I did not see the need for separate DAOs for each entity in the database.
 * Instead, I am creating a single source of truth via this main repository.
 * It will be a testable, stateless abstraction over the persistence layer.
 * I will break the pattern of "no updating objects in the repository" because that just seems completely arbitrary.
 */
public interface MainRepository {
    //APPOINTMENTS
    ObservableList<Appointment> getAppointmentsForUserByMonth(User user);
    ObservableList<Appointment> getAppointmentsForUserByWeek(User user);

    void addAppointment(Appointment appointment);
    void updateAppointment(int appointmentId, Appointment newAppointment);
    void removeAppointment(int appointmentId);

    //CUSTOMERS
    ObservableList<Customer> getAllCustomers();

    void addCustomer(Customer customer);
    void updateCustomer(int customerId, Customer newCustomer);
    void removeCustomer(int customerId);

    //User
    User getUserByUserName(String userName);
}
