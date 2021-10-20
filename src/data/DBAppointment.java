package data;

import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import test.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBAppointment {
    //TODO - if inserting a new appointment, will need to reference customer table as well
    // - Need to retrieve the getKey method

    //FIXME - An appointment object should be created for validation purposes and passed in to this method
    // as it will provide the necessary Customer, User, and Contact objects without any unnecessary stuff.
    // The id fields should derived from the appointment object created. Additionally, you must take into
    // care the instantiation of the fields later on (the timestamps), so an Appointment object is the best
    // solution for a modular makeup of this method for the time being.
    public static void insertAppointment(Appointment appointment){
//            String contactName,
//            String contactEmail,
//            String appointmentTitle,
//            String appointmentDescription,
//            String appointmentLocation,
//            String appointmentType,
//            Customer customer_customerId,
//            User user_userId,
//            Contact contact_contactId

        try {
            String sql_contact = "INSERT INTO client_schedule.contacts VALUES (NULL, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql_contact);
            ps.setString(1, contactName);
            ps.setString(2, contactEmail);
            ps.execute();
            ResultSet resultSet = ps.getGeneratedKeys();
            resultSet.next();
            int contactIdKey = resultSet.getInt(1);

            String sql_appointment = "INSERT INTO client_schedule.appointments VALUES(NULL, ?, ?, ?, ?, NULL, NULL, NULL, NULL, CURRENT_TIMESTAMP, NULL, ?, ?, ?)";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql_appointment);
            preparedStatement.setString(1, appointmentTitle);
            preparedStatement.setString(2, appointmentDescription);
            preparedStatement.setString(3, appointmentLocation);
            preparedStatement.setString(4, appointmentType);
            preparedStatement.setInt(5, customerId);
            preparedStatement.setInt(6, userId);
            preparedStatement.setInt(7, contactIdKey);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
