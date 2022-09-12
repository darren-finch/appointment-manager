package com.darrenfinch.appointmentmanager.common.data;

import com.darrenfinch.appointmentmanager.common.data.entities.*;
import com.darrenfinch.appointmentmanager.screens.dashboard.CustomerHasAppointmentsException;
import com.darrenfinch.appointmentmanager.screens.dashboard.CustomerWithLocationData;
import com.darrenfinch.appointmentmanager.screens.dashboard.DashboardController;
import com.darrenfinch.appointmentmanager.screens.reports.ContactSchedule;
import com.darrenfinch.appointmentmanager.screens.reports.NumberOfContactAppointmentsForMonth;
import com.darrenfinch.appointmentmanager.screens.reports.NumberOfCustomerAppointmentsForTypeAndMonth;
import javafx.collections.ObservableList;

/**
 *
 */
public interface MainRepository {
    // TODO: REMOVE FROM PUBLIC INTERFACE
    void initializeStaticData();

    // APPOINTMENTS
    ObservableList<Appointment> getUpcomingAppointmentsForUser(int userId); // returns all appointments in the future of the current date
    // This query contains a cross-cutting concern, because the viewByTimeFrame is a view-specific matter, but it needs to be specified in the query.
    // One way this could be mitigated is by having a specific DAO to do this.
    // If we have too many more of these types of methods it might be a good idea to start using a use-case oriented pattern or a dao pattern.
    ObservableList<Appointment> getAppointmentsForUserBySortingFilter(int userId, DashboardController.AppointmentsSortingFilter appointmentsSortingFilter);
    ObservableList<Appointment> getAppointmentsForCustomer(int customerId);
    ObservableList<Appointment> getAppointmentsForContact(int contactId);
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
    void removeCustomer(int customerId) throws CustomerHasAppointmentsException;

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
    ObservableList<NumberOfCustomerAppointmentsForTypeAndMonth> getNumberOfCustomerAppointmentsByTypeAndMonth(String type, String month);
    ObservableList<ContactSchedule> getContactSchedules();
    ObservableList<NumberOfContactAppointmentsForMonth> getNumberOfContactAppointmentsForMonth(String month);
}
