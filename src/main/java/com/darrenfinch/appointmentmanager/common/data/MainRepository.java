package com.darrenfinch.appointmentmanager.common.data;

import com.darrenfinch.appointmentmanager.common.data.entities.*;
import com.darrenfinch.appointmentmanager.screens.dashboard.CustomerWithLocationData;
import com.darrenfinch.appointmentmanager.screens.dashboard.DashboardController;
import com.darrenfinch.appointmentmanager.screens.reports.ContactSchedule;
import com.darrenfinch.appointmentmanager.screens.reports.NumberOfCustomerAppointmentsForContact;
import com.darrenfinch.appointmentmanager.screens.reports.NumberOfCustomerAppointmentsForTypeAndMonth;
import javafx.collections.ObservableList;

import java.time.ZonedDateTime;

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
    ObservableList<Appointment> getUpcomingAppointmentsForUser(int userId); // returns all appointments in the future of the current date
    ObservableList<Appointment> getAppointmentsForUserByTimeFrame(int userId, DashboardController.ViewByTimeFrame viewByTimeFrame);
    ObservableList<Appointment> getAppointmentsForCustomer(int customerId);
    Appointment getAppointment(int appointmentId);
    void addAppointment(Appointment appointment, User currentUser);
    void updateAppointment(int appointmentId, Appointment newAppointment, User currentUser);
    void removeAppointment(int appointmentId);

    // Customers with location data
    ObservableList<CustomerWithLocationData> getAllCustomersWithLocationData();

    //CUSTOMERS
    ObservableList<Customer> getAllCustomers();
    Customer getCustomer(int customerId);
    void addCustomer(Customer customer, User currentUser);
    void updateCustomer(int customerId, Customer newCustomer, User currentUser);
    void removeCustomer(int customerId);

    //User
    ObservableList<User> getAllUsers();
    User getUserByUserName(String userName);

    // Country
    ObservableList<Country> getAllCountries();

    // First Level Division
    ObservableList<FirstLevelDivision> getFirstLevelDivisionsForCountry(Country country);

    // Contacts
    ObservableList<Contact> getAllContacts();

    // Reports
    ObservableList<NumberOfCustomerAppointmentsForTypeAndMonth> getNumberOfCustomerAppointmentsByTypeAndMonth();
    ObservableList<ContactSchedule> getContactSchedules();
    ObservableList<NumberOfCustomerAppointmentsForContact> getNumberOfAppointmentsByContact();
}
