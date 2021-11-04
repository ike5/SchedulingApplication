package model;

import data.DBContacts;
import data.DBCustomers;
import data.DBUsers;

import java.time.LocalDateTime;

/**
 * Rules: An appointment cannot be made without a customer, user, and contact
 */
public class Appointment {
    // Everything except title and location can be null
    private int appointmentId;         // autogenerated
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

    @Deprecated
    public Appointment(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, Customer customer_customerId, User user_userId, Contact contact_contactId) {
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.customer = customer_customerId;
        this.user = user_userId;
        this.contact = contact_contactId;
        setIds();
    }

    public Appointment(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.start = start;
        this.end = end;
        this.startString = getStart().toString();
        this.endString = getEnd().toString();
        this.customer = DBCustomers.getCustomer(customerId);
        this.user = DBUsers.getUser(userId);
        this.contact = DBContacts.getContact(contactId);
        setIds();
    }

    // Used for the retrieval from database
    public Appointment(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, Customer customer, User user, Contact contact) {
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.start = start;
        this.end = end;
        this.customer = customer;
        this.user = user;
        this.contact = contact;
        setIds();
    }

    private void setIds() {
        this.customerId = customer.getId();
        this.userId = user.getUserId();
        this.contactId = contact.getContactId();
        this.contactName = contact.getContactName();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


    public String getStartString() {
        return startString;
    }

    public void setStartString(String startString) {
        this.startString = startString;
    }

    public String getEndString() {
        return endString;
    }

    public void setEndString(String endString) {
        this.endString = endString;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    //FIXME - Location is not specific: is it a Division? is it separate and made up by the user?
    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public User getUser() {
        return user;
    }

    public Contact getContact() {
        return contact;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", appointmentTitle='" + appointmentTitle + '\'' +
                ", appointmentDescription='" + appointmentDescription + '\'' +
                ", appointmentLocation='" + appointmentLocation + '\'' +
                ", appointmentType='" + appointmentType + '\'' +
                ", customer=" + customer +
                ", user=" + user +
                ", contact=" + contact +
                '}';
    }
}

