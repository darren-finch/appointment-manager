package com.darrenfinch.appointmentmanager.common.data;

import com.darrenfinch.appointmentmanager.common.data.entities.*;
import com.darrenfinch.appointmentmanager.screens.dashboard.DashboardController;
import javafx.collections.ObservableList;

/**
 * This is a simple enough application that I did not see the need for separate DAOs for each entity in the database.
 * Instead, I am creating a single source of truth via this main repository.
 * I will break the pattern of "no updating objects in the repository" because that just seems completely arbitrary.
 */
public interface MainRepository {
    void initializeStaticData();

    // APPOINTMENTS
    // This query contains a cross-cutting concern, because the viewByTimeFrame is a view-specific matter, but it needs to be specified in the query.
    // TODO: REFACTOR
    ObservableList<Appointment> getAppointmentsForUserByTimeFrame(int userId, DashboardController.ViewByTimeFrame viewByTimeFrame);

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

    // Country
    ObservableList<Country> getAllCountries();

    // First Level Division
    ObservableList<FirstLevelDivision> getFirstLevelDivisionsForCountry(Country country);
}
