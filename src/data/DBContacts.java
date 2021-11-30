package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Ike Maldonado
 * @version 1.0
 */
public class DBContacts {
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

}
