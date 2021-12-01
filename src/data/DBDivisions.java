package data;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides READ functionality to the first_level_divisions database table.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public class DBDivisions {

    /**
     * Gets all divisions provided a countryId.
     *
     * @return A ResultSet object containing all listed Division_ID, Divisions related to the Country ID.
     */
    public static ObservableList<Division> getDivisions(int countryId) {
        ObservableList<Division> divisionObservableList = FXCollections.observableArrayList();

        String sql = "SELECT client_schedule.first_level_divisions.Division_ID,\n" +
                "       client_schedule.first_level_divisions.Division,\n" +
                "       client_schedule.countries.Country_ID,\n" +
                "       client_schedule.countries.Country\n" +
                "FROM countries, first_level_divisions\n" +
                "WHERE (client_schedule.first_level_divisions.COUNTRY_ID = client_schedule.countries.Country_ID)\n" +
                "AND (client_schedule.countries.Country_ID = ?);";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryId);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Division division = new Division(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );
                divisionObservableList.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionObservableList;
    }

    /**
     * Returns a list of all Division objects.
     *
     * @return Returns an ObservableList<Division>
     */
    public static ObservableList<Division> getAllFirstLevelDivisions() {
        ObservableList<Division> divisionObservableList = FXCollections.observableArrayList();

        String sql = "SELECT client_schedule.first_level_divisions.Division_ID,\n" +
                "       client_schedule.first_level_divisions.Division,\n" +
                "       client_schedule.countries.Country_ID,\n" +
                "       client_schedule.countries.Country\n" +
                "FROM countries, first_level_divisions\n" +
                "WHERE (client_schedule.first_level_divisions.COUNTRY_ID = client_schedule.countries.Country_ID);";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Division division = new Division(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );
                divisionObservableList.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionObservableList;
    }
}
