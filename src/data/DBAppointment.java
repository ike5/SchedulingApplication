package data;

import model.Appointment;
import model.Contact;
import test.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBAppointment {
    //TODO - if inserting a new appointment, will need to reference customer table as well
    // - Need to retrieve the getKey method

    public static void insertAppointment(
            String contactName,
            String contactEmail,
            String appointmentTitle,
            String appointmentDescription,
            String appointmentLocation,
            String appointmentType,
            int customerId,
            int userId
    ) {

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
