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
 * Provides a primary Facade for the rest of the Application to use for interacting with the database.
 * It manages the database connection as well as the Result Set for each query.
 *
 * Note that none of these operations are multi-threaded. Instead, the multi-threading is done in the UI layer.
 * This was a deliberate decision because JavaFX provides the <code>Task</code> class that works quite well for
 * running background tasks.
 */
public interface MainRepository {
    /**
     * Initializes all the data that cannot be changed by a user. This includes users, countries, and first level divisions.
     * This allows us to quickly query this data without needing to put it in a background thread.
     */
    void initializeStaticData();



    // ***** APPOINTMENTS *****

    /**
     * Gets any upcoming appointments for a user (any appointments with a start date greater than the current date).
     */
    ObservableList<Appointment> getUpcomingAppointmentsForUser(int userId); // returns all appointments in the future of the current date

    /**
     * Gets appointments assigned to a user id and filtered by the specified time filter.
     */
    ObservableList<Appointment> getAppointmentsForUserByTimeFilter(int userId, TimeFilter timeFilter);

    /**
     * Gets all appointments assigned to the customer id.
     */
    ObservableList<Appointment> getAppointmentsForCustomer(int customerId);

    /**
     * Gets all appointments assigned to the contact id.
     */
    ObservableList<Appointment> getAppointmentsForContact(int contactId);

    /**
     * Gets an appointment based on its id.
     */
    Appointment getAppointment(int appointmentId);

    /**
     * Adds an appointment and assigns it to the current user. The id of the new appointment is auto-generated.
     */
    void addAppointment(Appointment appointment, User currentUser);

    /**
     * Updates the appointment retrieved for appointmentId with the information found in newAppointment.
     * The new appointment is assigned to the currentUser.
     */
    void updateAppointment(int appointmentId, Appointment newAppointment, User currentUser);

    /**
     * Removes the appointment with appointmentId.
     */
    void removeAppointment(int appointmentId);



    // ***** CUSTOMERS WITH LOCATION DATA *****

    /**
     * Gets all customers with location data.
     */
    ObservableList<CustomerWithLocationData> getAllCustomersWithLocationData();



    // ***** CUSTOMERS *****

    /**
     * Gets all the customers in the database.
     */
    ObservableList<Customer> getAllCustomers();

    /**
     * Gets a customer with an id.
     */
    Customer getCustomer(int customerId);

    /**
     * Adds a new customer and shows the creator to be current user.
     */
    void addCustomer(Customer customer, User currentUser);

    /**
     * Updates the customer with the customerId and shows the customer was last updated by the current user.
     */
    void updateCustomer(int customerId, Customer newCustomer, User currentUser);

    /**
     * Removes the customer with the customerId.
     */
    void removeCustomer(int customerId) throws CustomerHasAppointmentsException;



    // ***** USERS *****

    /**
     * Gets all the users in the database.
     * This does not need to be put on a background thread because this function returns a static list without accessing the database.
     */
    ObservableList<User> getAllUsers();

    /**
     * Gets a specific user by their username. It must be an exact match, since this is used for logging in.
     * This does not need to be put on a background thread because this function returns a static list without accessing the database.
     */
    User getUserByUserName(String userName);



    // ***** COUNTRIES *****

    /**
     * Gets all the countries in the database.
     * This does not need to be put on a background thread because this function returns a static list without accessing the database.
     */
    ObservableList<Country> getAllCountries();



    // ***** FIRST LEVEL DIVISIONS *****

    /**
     * Gets all the first level divisions (such as states or provinces) in the database.
     * This does not need to be put on a background thread because this function returns a static list without accessing the database.
     */
    ObservableList<FirstLevelDivision> getFirstLevelDivisionsForCountry(Country country);



    // ***** CONTACTS *****

    /**
     * Gets all the contacts in the database.
     * This does not need to be put on a background thread because this function returns a static list without accessing the database.
     */
    ObservableList<Contact> getAllContacts();



    // ***** REPORTS *****

    /**
     * Gets a list of rows representing the number of appointments each customer currently has coming up or has been a part of previously, filtered by appointment type.
     */
    ObservableList<NumberOfAppointmentsForCustomer> getNumberOfCustomerAppointmentsForType(String type);

    /**
     * Gets a list of rows representing the number of appointments each customer currently has coming up or has been a part of previously, filtered by month and the current year.
     */
    ObservableList<NumberOfAppointmentsForCustomer> getNumberOfCustomerAppointmentsForMonthThisYear(String month);

    /**
     * Gets a list of rows representing all upcoming appointments for each contact.
     */
    ObservableList<ContactAppointment> getUpcomingScheduleForContact(int contactId);

    /**
     * Gets a list of rows representing the number of appointments the selected contact currently has coming up or has been a part of previously, filtered by week, month, or all time.
     */
    ObservableList<NumberOfAppointmentsForContact> getNumberOfAppointmentsForContactAndTimeFilter(TimeFilter timeFilter);
}
