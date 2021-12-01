package data;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import controller.ReportsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.util.Pair;
import main.Main;
import model.*;
import test.Test;

import java.sql.*;
import java.time.*;
import java.util.*;

/**
 * This class makes provides CRUD functionality for appointments database table.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public class DBAppointment {
    /**
     * Returns an ObservableList of all Appointments in the database.
     *
     * @return Returns an ObservableList<Appointment>
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            addToAppointmentList(appointmentList, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }


    /**
     * Counts the total number of appointments in the database.
     *
     * @return Returns an integer of the total number of appointments.
     */
    public static Integer getTotalNumberOfAppointments() {
        Integer counter = 0;

        for (Appointment a : getAllAppointments()) {
            counter++;
        }

        return counter;
    }

    /**
     * Inserts an appointment into the appointments database table.
     *
     * @param appointmentTitle       A String appointment title
     * @param appointmentDescription A String appointment description
     * @param appointmentLocation    A String appointment location
     * @param appointmentType        A String appointment type
     * @param start                  The start time of the appointment as a LocalDateTime
     * @param end                    The end time of the appointment as a LocalDateTime
     * @param customer               A Customer object
     * @param user                   A User object
     * @param contact                A Contact object
     */
    public static void insertAppointment(String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, Customer customer, User user, Contact contact) {
        String sql_appointment = "INSERT INTO client_schedule.appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?)";
        try {
            // Insert new appointment into appointments database table
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql_appointment, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, appointmentTitle);
            ps.setString(2, appointmentDescription);
            ps.setString(3, appointmentLocation);
            ps.setString(4, appointmentType);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setString(7, Main.user.getUsername());
            ps.setString(8, Main.user.getUsername());
            ps.setInt(9, customer.getId());
            ps.setInt(10, user.getUserId());
            ps.setInt(11, contact.getContactId());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the total number of appointments by a specified month.
     *
     * @param month A Month enum
     * @return Returns an Integer representing the total number of
     * appointments in that month.
     */
    public static Integer getTotalNumberOfAppointmentsByMonth(java.time.Month month) {
        Integer numberOfAppointments = 0;
        String sql = "SELECT COUNT(Appointment_ID) AS NumberOfAppointments FROM appointments WHERE MONTH(Start) = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1, month.getValue());

            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            numberOfAppointments = resultSet.getInt("NumberOfAppointments");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfAppointments;
    }

    /**
     * Returns the total number of appointments by a specified type.
     *
     * @param type A String type of appointment
     * @return Returns an Integer representing the total number of
     * appointments of that type.
     */
    public static Integer getNumberOfAppointmentsByType(String type) {
        Integer numberOfAppointments = 0;

        String sql = "SELECT COUNT(Appointment_ID) AS NumberOfAppointments FROM appointments WHERE Type = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, type);

            ResultSet resultSet = ps.executeQuery();
            resultSet.next();

            numberOfAppointments = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfAppointments;
    }

    /**
     * Returns the total number of appointments that are in the same month
     * and are of the same type.
     *
     * @param month A Month enum
     * @param type  A String type of appointment
     * @return Returns an Integer representing the total number of
     * appointments in specified month of specified type.
     */
    public static Integer getNumberOfAppointmentsByMonthAndType(Month month, String type) {
        Integer numberOfAppointments = 0;

        String sql = "SELECT COUNT(Appointment_ID) AS NumberOfAppointments FROM appointments WHERE MONTH(Start) = ? AND Type = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, month.getValue());
            ps.setString(2, type);

            ResultSet resultSet = ps.executeQuery();
            resultSet.next();

            numberOfAppointments = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return numberOfAppointments;
    }

    /**
     * Returns an ObservableList of all appointments in a specified month.
     *
     * @return ObservableList<Appointment>
     */
    public static ObservableList<Appointment> getAllAppointmentsInMonth() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments WHERE Start >= ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now().minusMonths(1)));

            ResultSet resultSet = ps.executeQuery();

            addToAppointmentList(appointmentList, resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Returns an ObservableList of all appointments in specified week.
     *
     * @return ObservableList<Appointment>
     */
    public static ObservableList<Appointment> getAllAppointmentsInWeek() {
        String sql = "SELECT * FROM appointments WHERE Start >= ?";

        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now().minusDays(7)));

            ResultSet resultSet = ps.executeQuery();

            addToAppointmentList(appointmentList, resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Returns a List of all appointments belonging to a specified
     * customer.
     *
     * @param customerId An int value representing a specified customer
     * @return Returns a List<Appointment>
     */
    public static List<Appointment> getAllAppointmentsByCustomerId(int customerId) {
        List<Appointment> appointmentList = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
            ps.setInt(1, customerId);

            ResultSet resultSet = ps.executeQuery();

            addToAppointmentList(appointmentList, resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Helper method to add to a specified List<Appointment> object. For
     * use when retrieving multiple items from the appointments database.
     *
     * @param appointmentList Takes a list of appoint
     * @param resultSet       The ResultSet database object retrieved.
     * @throws SQLException
     */
    private static void addToAppointmentList(List<Appointment> appointmentList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Timestamp ts_start = resultSet.getTimestamp("Start");
            Timestamp ts_end = resultSet.getTimestamp("End");
            LocalDateTime ldt_start = ts_start.toLocalDateTime();
            LocalDateTime ldt_end = ts_end.toLocalDateTime();


            Appointment appointment = new Appointment(
                    resultSet.getInt("Appointment_ID"),
                    resultSet.getString("Title"),
                    resultSet.getString("Description"),
                    resultSet.getString("Location"),
                    resultSet.getString("Type"),
                    ldt_start,
                    ldt_end,
                    resultSet.getInt("Customer_ID"),
                    resultSet.getInt("User_ID"),
                    resultSet.getInt("Contact_ID")
            );

            appointmentList.add(appointment);
        }
    }

    /**
     * This method executes updates on the appointments table.
     *
     * @param appointmentId          An int appointment id to make the update on
     * @param appointmentTitle       A String appointment title
     * @param appointmentDescription A String appointment description
     * @param appointmentLocation    A String appointment location
     * @param appointmentType        A String appointment type
     * @param start                  The start time of the appointment as a LocalDateTime
     * @param end                    The end time of the appointment as a LocalDateTime
     * @param customer               A Customer object
     * @param user                   A User object
     * @param contact                A Contact object
     */
    public static void updateAppointment(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, Customer customer, User user, Contact contact) {
        String sql = "UPDATE appointments Set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = CURRENT_TIMESTAMP, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, appointmentTitle);
            ps.setString(2, appointmentDescription);
            ps.setString(3, appointmentLocation);
            ps.setString(4, appointmentType);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setString(7, Main.user.getUsername());
            ps.setInt(8, customer.getId());
            ps.setInt(9, user.getUserId());
            ps.setInt(10, contact.getContactId());
            ps.setInt(11, appointmentId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method deletes an appointment from the database table provided
     * an appointmentId that exists.
     *
     * @param appointmentId An int appointment id
     * @return Returns true if delete is successful.
     */
    public static boolean deleteAppointment(int appointmentId) {
        boolean isDeleted = false;

        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentId);

            ps.executeUpdate();

            isDeleted = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isDeleted;
    }

    /**
     * Returns an ObservableList of appointments where a specified Contact
     * object is provided.
     *
     * @param contact A specified Contact object
     * @return ObservableList<Appointment>
     */
    public static ObservableList<Appointment> getAppointmentListFromContact(Contact contact) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, contact.getContactId());

            ResultSet resultSet = ps.executeQuery();

            addToAppointmentList(appointmentList, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * This method checks for upcoming appointments within the next 15
     * minutes belonging to the user logged in.
     *
     * @return Returns a Pair whose boolean value is true if an appointment
     * exists in the next 15 minutes and, if so, provides another Pair object
     * of the upcoming appointment time and id. Otherwise, returns false
     * and null.
     */
    public static Pair<Boolean, Pair<LocalDateTime, Integer>> checkUpcomingAppointments() {
        List<Pair<LocalDateTime, Integer>> localDateTimeAppointmentIdList = new ArrayList<>();
        Pair<Boolean, Pair<LocalDateTime, Integer>> upcomingAppointment = null;

        String sql = "SELECT Appointment_ID, Start FROM appointments WHERE User_ID = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, Main.user.getUserId());

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                // Retrieve appointment start time from database
                localDateTimeAppointmentIdList.add(
                        new Pair(resultSet.getTimestamp(2).toLocalDateTime(), resultSet.getInt(1))
                );
            }

            // Create a duration of 15 minutes
            Duration duration = Duration.ofMinutes(15);

            // Get current time
            ZonedDateTime currentTime = ZonedDateTime.now();

            // Iterate through all LocalDateTime and AppointmentID retrieved from database and see
            // if there are any appointments within 15 minutes of current time.
            for (Pair<LocalDateTime, Integer> timeAppointmentIdPair : localDateTimeAppointmentIdList) {

                // Convert to zoned datetime matching user zone
                ZonedDateTime zonedDateTime = ZonedDateTime.of(timeAppointmentIdPair.getKey(), ZoneId.systemDefault());

                if (zonedDateTime.isAfter(currentTime) && zonedDateTime.isBefore(currentTime.plusMinutes(duration.toMinutes()))) {
                    upcomingAppointment = new Pair<>(Boolean.TRUE, timeAppointmentIdPair);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upcomingAppointment;
    }
}
