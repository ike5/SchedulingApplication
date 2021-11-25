package model;

import data.DBContacts;
import data.DBCustomers;
import data.DBUsers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Appointment objects are made with a LocalDateTime object that must be converted to a ZonedDateTime
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

    public Appointment(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime localDateTime_start, LocalDateTime localDateTime_end, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.start = localDateTime_start;
        this.end = localDateTime_end;

        ZonedDateTime zonedDateTime_start = ZonedDateTime.of(localDateTime_start, ZoneId.systemDefault());
        ZonedDateTime zonedDateTime_end = ZonedDateTime.of(localDateTime_end, ZoneId.systemDefault());
        ZoneId easternZoneId = ZoneId.of("America/New_York");
        ZonedDateTime zonedDateTime_start_EST = ZonedDateTime.ofInstant(zonedDateTime_start.toInstant(), easternZoneId);
        ZonedDateTime zonedDateTime_end_EST = ZonedDateTime.ofInstant(zonedDateTime_end.toInstant(), easternZoneId);

        this.startString = zonedDateTime_start_EST.toString();
        this.endString = zonedDateTime_end_EST.toString();
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

