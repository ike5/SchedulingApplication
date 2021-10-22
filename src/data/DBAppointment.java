package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.sql.*;

//TODO
// - Need a READ method
// - Need a DELETE method
// - Need an UPDATE method
public class DBAppointment {
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Appointment appointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID")
                );
                appointmentObservableList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentObservableList;
    }



    /**
     * The CREATE method. Inserts a new appointment into the appointments database table and returns an Appointment object.
     *
     * @param appointmentTitle
     * @param appointmentDescription
     * @param appointmentLocation
     * @param appointmentType
     * @param customerObj_customerId
     * @param userObj_userId
     * @param contactName
     * @param contactEmail
     * @return Appointment object
     */
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
            // Create a Contact object
            while (resultSetGetContact.next()) {
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

            // Get key for generated appointment
            ResultSet resultSetAppointment = preparedStatement.getGeneratedKeys();
            resultSetAppointment.next();
            int appointmentKey = resultSetAppointment.getInt(1);

            // Retrieve appointment row from database
            String sql_get_appointment = "SELECT Appointment_ID, Title, Description, Location, Type, Customer_ID, User_ID, Contact_ID FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement preparedStatementGetAppointment = JDBC.getConnection().prepareStatement(sql_get_appointment, Statement.NO_GENERATED_KEYS);
            preparedStatementGetAppointment.setInt(1, appointmentKey);
            ResultSet resultSetGetAppointment = preparedStatementGetAppointment.executeQuery();
            Appointment appointment = null;

            // Create an Appointment object
            while (resultSetGetAppointment.next()) {
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

//            // APPOINTMENT TEST VALUES (unnecessary for this method)
//            String title = "default title";
//            String description = "default description of things";
//            String location = "default location";
//            String type = "default type";
//            String created_by = "default created_by";
//            String last_updated_by = "default last_updated_by";
//            int user_id = 1; // test
//            int contact_id = 3; // Li Lee
//
//            // Take acquired ID from above and put it into the next insert
//            String sql_app = "INSERT INTO appointments VALUES (null, ?, ?, ?, ?, current_timestamp, current_timestamp, current_timestamp, ?, current_timestamp, ?, ?, ?, ?)";
//            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql_app);
//            preparedStatement.setString(1, title);
//            preparedStatement.setString(2, description);
//            preparedStatement.setString(3, location);
//            preparedStatement.setString(4, type);
//            preparedStatement.setString(5, created_by);
//            preparedStatement.setString(6, last_updated_by);
//            preparedStatement.setInt(7, customerId);
//            preparedStatement.setInt(8, user_id);
//            preparedStatement.setInt(9, contact_id);
//
//            preparedStatement.execute();