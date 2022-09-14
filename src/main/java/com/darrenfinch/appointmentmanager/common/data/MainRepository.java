package com.darrenfinch.appointmentmanager.common.data;

import com.darrenfinch.appointmentmanager.common.data.entities.*;
import com.darrenfinch.appointmentmanager.common.data.misc.TimeFilter;
import com.darrenfinch.appointmentmanager.screens.dashboard.CustomerHasAppointmentsException;
import com.darrenfinch.appointmentmanager.screens.dashboard.CustomerWithLocationData;
import com.darrenfinch.appointmentmanager.screens.reports.ContactAppointment;
import com.darrenfinch.appointmentmanager.screens.reports.NumberOfAppointmentsForContact;
import com.darrenfinch.appointmentmanager.screens.reports.NumberOfAppointmentsForCustomer;
import javafx.collections.ObservableList;

/**
 *
 */
public interface MainRepository {
    void initializeStaticData();

    // APPOINTMENTS
    ObservableList<Appointment> getUpcomingAppointmentsForUser(int userId); // returns all appointments in the future of the current date
    ObservableList<Appointment> getAppointmentsForUserByTimeFilter(int userId, TimeFilter timeFilter);
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
    ObservableList<NumberOfAppointmentsForCustomer> getNumberOfCustomerAppointmentsForType(String type);
    ObservableList<NumberOfAppointmentsForCustomer> getNumberOfCustomerAppointmentsForMonthThisYear(String month);
    ObservableList<ContactAppointment> getUpcomingScheduleForContact(int contactId);
    ObservableList<NumberOfAppointmentsForContact> getNumberOfAppointmentsForContactAndTimeFilter(TimeFilter timeFilter);
}
