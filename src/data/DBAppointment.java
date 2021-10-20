package data;

import jdk.jfr.Description;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import test.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAppointment {
    //FIXME - The only way to get an Appointment ID is to have it retrieved from the Database. It is not possible to create
    // an Appointment object without an item in the database. Therefore you must make related objects in the database
    // before making an Appointment object. Related objects like Customer and User are already created (so you can pass an object),
    // but Contact is not created until you make an appointment, so you will need to get the generatedKeyID from Contact
    // after you create it.
    public static void main(String[] args) {
        JDBC.openConnection();
        Customer customer = DBCustomers.getCustomer(1);
        User user = new User();
        System.out.println(insertAppointment("Title of appointment", "Description of Appointment", "London", "Briefing of things", customer, user, "Joey Contacty", "joey@gmail.com"));
    }

    public static Appointment insertAppointment(String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, Customer customerObj_customerId, User userObj_userId, String contactName, String contactEmail) {
        try {
            // Insert into the contacts database table (works)
            String sql_contact = "INSERT INTO client_schedule.contacts VALUES (NULL, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql_contact, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, contactName);
            ps.setString(2, contactEmail);
            ps.execute();
            // The following ResultSet has only one column: key (works)
            ResultSet resultSet = ps.getGeneratedKeys();    // Cannot use this result set for anything else
            resultSet.next();
            int contactIdKey = resultSet.getInt(1);

            // Create the Contact object (You need a new query) (workds)
            String sql_getContact = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement preparedStatementContact = JDBC.getConnection().prepareStatement(sql_getContact, Statement.NO_GENERATED_KEYS);
            preparedStatementContact.setInt(1, contactIdKey);
            ResultSet resultSetGetContact = preparedStatementContact.executeQuery();

            Contact contactObject = null;
            while(resultSetGetContact.next()){
                contactObject = new Contact(
                        resultSetGetContact.getInt(1),
                        resultSetGetContact.getString(2),
                        resultSetGetContact.getString(3)
                );
            }

            // Insert new appointment into appointments database table
            String sql_appointment = "INSERT INTO client_schedule.appointments VALUES(NULL, ?, ?, ?, ?, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, NULL, ?, ?, ?)";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql_appointment, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, appointmentTitle);
            preparedStatement.setString(2, appointmentDescription);
            preparedStatement.setString(3, appointmentLocation);
            preparedStatement.setString(4, appointmentType);
            preparedStatement.setInt(5, customerObj_customerId.getId());
            preparedStatement.setInt(6, 1); //FIXME - replace with an actual User object: user.getId()
            preparedStatement.setInt(7, contactIdKey);

            preparedStatement.execute();

            ResultSet resultSetAppointment = preparedStatement.getGeneratedKeys();
            resultSetAppointment.next();
            int appointmentKey = resultSetAppointment.getInt(1);

            String sql_get_appointment = "SELECT Appointment_ID, Title, Description, Location, Type, Customer_ID, User_ID, Contact_ID FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement preparedStatementGetAppointment = JDBC.getConnection().prepareStatement(sql_get_appointment, Statement.NO_GENERATED_KEYS);
            preparedStatementGetAppointment.setInt(1, appointmentKey);
            ResultSet resultSetGetAppointment = preparedStatementGetAppointment.executeQuery();
            Appointment appointment = null;

            while (resultSetGetAppointment.next()){
                System.out.println(resultSetGetAppointment.getInt(1) +
                        "\t" + resultSetGetAppointment.getString(2));
                appointment = new Appointment(
                        resultSetGetAppointment.getInt(1),
                        resultSetGetAppointment.getString(2),
                        resultSetGetAppointment.getString(3),
                        resultSetGetAppointment.getString(4),
                        resultSetGetAppointment.getString(5),
                        customerObj_customerId,
                        userObj_userId,
                        contactObject);
            }

            return appointment;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
