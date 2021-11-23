package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContacts {

    /**
     * Create method. Updates the contacts database table to add a new contact.
     *
     * @param name  Name of contact
     * @param email Contact email address
     * @return Returns sentinel value >= 1 if add was successful and -1 if add was unsuccessful.
     */
    public static int addContact(String name, String email) {
        String sql = "INSERT INTO contacts (Contact_Name, Email) VALUES ('" + name + "' , '" + email + "')";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    /**
     * Returns the ResultSet object of all contacts listed in the contacts database table.
     *
     * @return ResultSet object
     */
    public static ResultSet getAllContactsResultSet() {
        String sql = "SELECT * FROM contacts";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Returns an ObservableList<Contact> object of all contacts listed in the contacts database table.
     *
     * @return
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contactObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Contact contact = new Contact(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email")
                );
                contactObservableList.add(contact);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactObservableList;
    }

    public static Integer getTotalNumberOfContacts() {
        Integer counter = 0;
        for (Contact c : getAllContacts()) {
            counter++;
        }

        return counter;
    }

    /**
     * @param contactId The contact ID
     * @return ResultSet object of the contact requested
     */
    public static Contact getContact(int contactId) {
        String sql = "SELECT * FROM contacts WHERE Contact_ID = " + contactId;
        Contact contact = null;
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                contact = new Contact(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email")
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contact;
    }

    /**
     * Returns the contact ID provided the contact name. This helper method is redundant and could
     * be circumvented with proper creation of a Contact object.
     *
     * @param contactName A String of the contact's full name
     * @return An integer representing the contact ID. Returns -1 if no such contact exists.
     */
    public static int getContactId(String contactName) {
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = " + contactName;
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    /**
     * Updates the contact name provided a contact ID and new name.
     *
     * @param contactId An integer representing the contact ID.
     * @param name      The contact name to change
     * @return Returns -1 if unsuccessful or > 1 if successful.
     */
    public static int updateContactName(int contactId, String name) {
        String sql = "UPDATE contacts SET Contact_Name = '" + name + "' WHERE Contact_ID = " + contactId + ";";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1; // unsuccessful
    }

    /**
     * Updates the contact email provided a contact ID and a new email.
     *
     * @param contactId An integer representing the contact ID.
     * @param email     The contact email to change
     * @return Returns -1 if unsuccessful or > 1 if successful.
     */
    public static int updateContactEmail(int contactId, String email) {
        String sql = "UPDATE contacts SET Email = '" + email + "' WHERE Contact_ID = " + contactId + ";";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeUpdate(); // 1 if successful
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1; // unsuccessful
    }

    /**
     * Deletes contact from contacts database table.
     *
     * @param contactId An integer representing the contact ID.
     * @return Returns -1 if unsuccessful or > 1 if successful.
     */
    public static int deleteContact(int contactId) {
        String sql = "DELETE FROM contacts WHERE Contact_ID = " + contactId + ";";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1; // unsuccessful
    }
}
