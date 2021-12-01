package model;

import data.DBContacts;
import data.DBCustomers;
import data.DBUsers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This class creates Appointment objects.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public class Appointment {
    private int appointmentId;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private Customer customer;
    private User user;
    private Contact contact;
    private String contactName;
    private int customerId;
    private int userId;
    private int contactId;
    private LocalDateTime start;
    private LocalDateTime end;
    private String startString;
    private String endString;

    public Appointment(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime localDateTime_start, LocalDateTime localDateTime_end, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.start = localDateTime_start;
        this.end = localDateTime_end;

        // Note that all time in application is set to EST
        ZoneId easternZoneId = ZoneId.of("America/New_York");

        ZonedDateTime zonedDateTime_start = ZonedDateTime.of(localDateTime_start, ZoneId.systemDefault());
        ZonedDateTime zonedDateTime_end = ZonedDateTime.of(localDateTime_end, ZoneId.systemDefault());
        ZonedDateTime zonedDateTime_start_EST = ZonedDateTime.ofInstant(zonedDateTime_start.toInstant(), easternZoneId);
        ZonedDateTime zonedDateTime_end_EST = ZonedDateTime.ofInstant(zonedDateTime_end.toInstant(), easternZoneId);

        this.startString = zonedDateTime_start_EST.toString();
        this.endString = zonedDateTime_end_EST.toString();
        this.customer = DBCustomers.getCustomer(customerId);
        this.user = DBUsers.getUser(userId);
        this.contact = DBContacts.getContact(contactId);
        setIds();
    }

    /**
     * Helper method to convert Customer, User, and Contact objects to
     * their respective ids.
     */
    private void setIds() {
        this.customerId = customer.getId();
        this.userId = user.getUserId();
        this.contactId = contact.getContactId();
        this.contactName = contact.getContactName();
    }

    /**
     * Sets Contact name value
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets Contact name value
     * @return Contact name String
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets start time string value
     * @param startString
     */
    public void setStartString(String startString) {
        this.startString = startString;
    }

    /**
     * Gets start time string value
     * @return Start time String
     */
    public String getStartString() {
        return startString;
    }


    /**
     * Sets end time string value
     * @param endString
     */
    public void setEndString(String endString) {
        this.endString = endString;
    }

    /**
     * Gets end time string value
     * @return End time String
     */
    public String getEndString() {
        return endString;
    }

    /**
     * Sets Customer id value
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets Customer id value
     * @return Customer id int
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets User id value
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets User id value
     * @return User id int
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets Contact id value
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets Contact id value
     * @return Contact id int
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets Appointment id value
     * @param appointmentId
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Sets Appointment title
     * @param appointmentTitle
     */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    /**
     * Sets Appointment description
     * @param appointmentDescription
     */
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    /**
     * Sets Appointment location
     * @param appointmentLocation
     */
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    /**
     * Sets Appointment type value
     * @param appointmentType
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    /**
     * Sets Customer value
     * @param customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sets User value
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets Contact value
     * @param contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Gets start time value
     * @return start LocalDateTime value
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets start time value
     * @param start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets end time value
     * @return end LocalDateTime value
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets end time value
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Gets Appointment id value
     * @return Appointment id int
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Gets Appointment title value
     * @return Appointment title String
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    /**
     * Gets Appointment Description value
     * @return Appointment description String
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /**
     * Gets Appointment location value
     * @return Appointment location String
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    /**
     * Gets Appointment type value
     * @return Appointment type value String
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * Gets Customer object
     * @return Returns a Customer object
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets User object
     * @return Returns a User object
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets Contact object
     * @return Returns a Contact object
     */
    public Contact getContact() {
        return contact;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "\nappointmentId=" + appointmentId +
                "\nappointmentTitle='" + appointmentTitle + '\'' +
                "\nappointmentDescription='" + appointmentDescription + '\'' +
                "\nappointmentLocation='" + appointmentLocation + '\'' +
                "\nappointmentType='" + appointmentType + '\'' +
                "\ncustomer=" + customer +
                "\nuser=" + user +
                "\ncontact=" + contact +
                "\nstartTime=" + startString +
                "\nendtime=" + endString +
                '}';
    }
}

