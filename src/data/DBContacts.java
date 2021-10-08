package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContacts {

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

    // FIXME - make return an ObservableList<Contact> ?
    public static ResultSet getAllContacts() {
        String sql = "SELECT * FROM contacts";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static ResultSet getContact(int contactId) {
        String sql = "SELECT * FROM contacts WHERE Contact_ID = " + contactId;
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

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

    public static int updateContactName(int contactId, String name) {
        String sql = "UPDATE contacts SET Contact_Name = '" + name + "' WHERE Contact_ID = " + contactId + ";";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactId;
    }

    /**
     * @param contactId
     * @param email
     * @return -1 if unsuccessful and 1 or greater if successful
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

    public static int deleteContact(int contactId) {
        String sql = "DELETE FROM contacts WHERE Contact_ID = " + contactId + ";";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}
