package model;

import data.DBContacts;
import data.DBCustomers;
import data.DBUsers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Appointment.
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

    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentId          the appointment id
     * @param appointmentTitle       the appointment title
     * @param appointmentDescription the appointment description
     * @param appointmentLocation    the appointment location
     * @param appointmentType        the appointment type
     * @param localDateTime_start    the local date time start
     * @param localDateTime_end      the local date time end
     * @param customerId             the customer id
     * @param userId                 the user id
     * @param contactId              the contact id
     */
    public Appointment(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime localDateTime_start, LocalDateTime localDateTime_end, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.start = localDateTime_start;
        this.end = localDateTime_end;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, uuuu hh:mm a");

        this.startString =  this.start.atZone(ZoneId.systemDefault()).format(formatter);
        this.endString = this.end.atZone(ZoneId.systemDefault()).format(formatter);

        this.customer = DBCustomers.getCustomer(customerId);
        this.user = DBUsers.getUser(userId);
        this.contact = DBContacts.getContact(contactId);
        setIds();
    }

    private void setIds() {
        this.customerId = customer.getId();
        this.userId = user.getUserId();
        this.contactId = contact.getContactId();
        this.contactName = contact.getContactName();
    }

    /**
     * Sets contact name.
     *
     * @param contactName the contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets contact name.
     *
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets start string.
     *
     * @param startString the start string
     */
    public void setStartString(String startString) {
        this.startString = startString;
    }

    /**
     * Gets start string.
     *
     * @return the start string
     */
    public String getStartString() {
        return startString;
    }


    /**
     * Sets end string.
     *
     * @param endString the end string
     */
    public void setEndString(String endString) {
        this.endString = endString;
    }

    /**
     * Gets end string.
     *
     * @return the end string
     */
    public String getEndString() {
        return endString;
    }

    /**
     * Sets customer id.
     *
     * @param customerId the customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets contact id.
     *
     * @param contactId the contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets appointment id.
     *
     * @param appointmentId the appointment id
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Sets appointment title.
     *
     * @param appointmentTitle the appointment title
     */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    /**
     * Sets appointment description.
     *
     * @param appointmentDescription the appointment description
     */
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    /**
     * Sets appointment location.
     *
     * @param appointmentLocation the appointment location
     */
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    /**
     * Sets appointment type.
     *
     * @param appointmentType the appointment type
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    /**
     * Sets customer.
     *
     * @param customer the customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets contact.
     *
     * @param contact the contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets end.
     *
     * @return the end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Sets end.
     *
     * @param end the end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Gets appointment id.
     *
     * @return the appointment id
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Gets appointment title.
     *
     * @return the appointment title
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    /**
     * Gets appointment description.
     *
     * @return the appointment description
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    /**
     * Gets appointment location.
     *
     * @return the appointment location
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    /**
     * Gets appointment type.
     *
     * @return the appointment type
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * Gets customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets contact.
     *
     * @return the contact
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

