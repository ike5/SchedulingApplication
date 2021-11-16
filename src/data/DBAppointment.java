package data;

import controller.ReportsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.*;
import test.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBAppointment {
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

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
                appointmentObservableList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentObservableList;
    }

    public static Appointment getAppointment(int appointmentId) {
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID " +
                "FROM appointments " +
                "WHERE Appointment_ID = ?";
        Appointment appointment = null;
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Timestamp timestampStart = resultSet.getTimestamp("Start");
                Timestamp timestampEnd = resultSet.getTimestamp("End");
                LocalDateTime localDateTimeStart = timestampStart.toLocalDateTime();
                LocalDateTime localDateTimeEnd = timestampEnd.toLocalDateTime();


                appointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Type"),
                        localDateTimeStart,
                        localDateTimeEnd,
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getInt("Contact_ID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointment;
    }

    public static void insertAppointment(String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, Customer customer, User user, Contact contact) {
        try {
            // Insert new appointment into appointments database table
            String sql_appointment = "INSERT INTO client_schedule.appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql_appointment, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, appointmentTitle);
            preparedStatement.setString(2, appointmentDescription);
            preparedStatement.setString(3, appointmentLocation);
            preparedStatement.setString(4, appointmentType);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
            preparedStatement.setString(7, Main.user.getUsername());
            preparedStatement.setString(8, Main.user.getUsername());
            preparedStatement.setInt(9, customer.getId());
            preparedStatement.setInt(10, user.getUserId());
            preparedStatement.setInt(11, contact.getContactId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets total number of appointments by type. Selects all types distinctly, then for each
     * type retrieves number of appointments.
     *
     * @param type
     * @return
     */
    private static int getTotalNumberOfAppointmentsByType(String type) {
        String sql = "SELECT COUNT(Appointment_ID) AS NumberOfAppointments FROM appointments WHERE Type = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, type);
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();
            return resultSet.getInt("NumberOfAppointments");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    private static int getTotalNumberOfAppointmentsByMonth(java.time.Month month) {
        String sql = "SELECT COUNT(Appointment_ID) AS NumberOfAppointments FROM appointments WHERE MONTH(Start) = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1, month.getValue());

            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getInt("NumberOfAppointments");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public static ObservableList<Map> getMapOfAppointmentsByMonth() {
        ObservableList<Map> mapObservableList = FXCollections.observableArrayList();
        for (Month month : Month.values()) {
            Map<String, String> mapDataRow = new HashMap<>();
            mapDataRow.put(ReportsController.MONTH_MAP_KEY, month.name());
            mapDataRow.put(ReportsController.NUM_APPOINTMENT_BY_MONTH_MAP_KEY, Integer.toString(getTotalNumberOfAppointmentsByMonth(month)));
            mapObservableList.add(mapDataRow);
        }
        return mapObservableList;
    }

    /**
     * Gets a list of all distinct types of appointments from the appointments database.
     *
     * @return
     */
    private static List<String> getAllTypes() {
        List<String> typeList = new ArrayList<>();

        String sql = "SELECT DISTINCT Type FROM appointments";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                typeList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeList;
    }

    public static ObservableList<Map> getMapOfTypesAndValue() {
        ObservableList<Map> mapObservableList = FXCollections.observableArrayList();

        for (String type : getAllTypes()) {
            Map<String, String> mapDataRow = new HashMap<>();

            //FIXME - The following Type and Map depend on the ReportsController.TYPE_MAP_KEY and .NUM_APPOINTMENT_MAP_KEY values
            // First eliminating them can help with plagerims if you didn't get this from the Javafx documentation.
            mapDataRow.put(ReportsController.TYPE_MAP_KEY, type);
            mapDataRow.put(ReportsController.NUM_APPOINTMENT_MAP_KEY, Integer.toString(getTotalNumberOfAppointmentsByType(type)));
            mapObservableList.add(mapDataRow);
        }
        return mapObservableList;
    }

    public static ObservableList<Appointment> getAllAppointmentsInMonth() {
        String sql = "SELECT * FROM appointments WHERE Start >= ?;";
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now().minusMonths(1)));
            ResultSet resultSet = ps.executeQuery();

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

                appointmentObservableList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentObservableList;
    }

    public static ObservableList<Appointment> getAllAppointmentsInWeek() {
        String sql = "SELECT * FROM appointments WHERE Start >= ?;";
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now().minusDays(7)));
            ResultSet resultSet = ps.executeQuery();

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

                appointmentObservableList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentObservableList;
    }

    public static void insertTestAppointment(String user) {
        // APPOINTMENT TEST VALUES (uncomment in Main to use)

        String title = "Bringing in the wealth";
        String description = "default description of things";
        String location = "default location";
        String type = "default type";
        String created_by = user;
        String last_updated_by = user;
        Timestamp timestampStart = Timestamp.valueOf(LocalDateTime.of(2021, 10, 20, 2, 2));
        Timestamp timestampEnd = Timestamp.valueOf(LocalDateTime.of(2021, 10, 22, 2, 2));

        int user_id = Main.user.getUserId(); // test
        int customer_id = DBCustomers.getCustomer(1).getId();
        int contact_id = 3; // Li Lee

        // Take acquired ID from above and put it into the next insert
        String sql_app = "INSERT INTO appointments VALUES (null, ?, ?, ?, ?, ?, ?, current_timestamp, ?, current_timestamp, ?, ?, ?, ?)";
        try { //5 6 are start and end
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql_app);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, type);
            preparedStatement.setTimestamp(5, timestampStart);
            preparedStatement.setTimestamp(6, timestampEnd);
            preparedStatement.setString(7, created_by);
            preparedStatement.setString(8, last_updated_by);
            preparedStatement.setInt(9, customer_id);
            preparedStatement.setInt(10, user_id);
            preparedStatement.setInt(11, contact_id);

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

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
     * @param appointmentId
     * @return Returns true if delete is successful.
     */
    public static boolean deleteAppointment(int appointmentId) {
        boolean flag = false;
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ps.executeUpdate();
            flag = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag;
    }

    public static ObservableList<Appointment> getAppointmentListFromContact(Contact contact) {
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, contact.getContactId());
            ResultSet resultSet = ps.executeQuery();

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

                appointmentObservableList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentObservableList;
    }

    public static Appointment getAppointmentByUser(){
        Appointment appointment = null;
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime minus15Minutes = localDateTime.minusMinutes(15);

        // if appointment.after(minus15Minutes) alert the user
        String sql = "SELECT Appointment_ID, Start FROM appointments WHERE User_ID = ?";
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            new Test(Main.user.getUserId());
            ps.setInt(1, Main.user.getUserId());
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                LocalDateTime localDateRetrieved = resultSet.getTimestamp(2).toLocalDateTime();

                Timestamp ts_start = resultSet.getTimestamp("Start");
                Timestamp ts_end = resultSet.getTimestamp("End");
                LocalDateTime ldt_start = ts_start.toLocalDateTime();
                LocalDateTime ldt_end = ts_end.toLocalDateTime();

                if(localDateRetrieved.isAfter(minus15Minutes)){
                    appointment = new Appointment(
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
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointment;
    }
}


    /*
    MySQL converts TIMESTAMP values from the current time zone to UTC for storage, and back from UTC to the current time zone for retrieval.
    (This does not occur for other types such as DATETIME.

    In MySQL 8.0.19 and later, you can specify a time zone offset when inserting a TIMESTAMP or DATETIME value into a table.
    See Section 9.1.3, “Date and Time Literals”, for more information and examples.

    In MySQL 8.0.22 and later, you can convert TIMESTAMP values to UTC DATETIME values when retrieving them using CAST()
    with the AT TIME ZONE operator, as shown here:

    mysql> SELECT col,
     >     CAST(col AT TIME ZONE INTERVAL '+00:00' AS DATETIME) AS ut
     >     FROM ts ORDER BY id;
+---------------------+---------------------+
| col                 | ut                  |
+---------------------+---------------------+
| 2020-01-01 10:10:10 | 2020-01-01 15:10:10 |
| 2019-12-31 23:40:10 | 2020-01-01 04:40:10 |
| 2020-01-01 13:10:10 | 2020-01-01 18:10:10 |
| 2020-01-01 10:10:10 | 2020-01-01 15:10:10 |
| 2020-01-01 04:40:10 | 2020-01-01 09:40:10 |
| 2020-01-01 18:10:10 | 2020-01-01 23:10:10 |
+---------------------+---------------------+
     */
