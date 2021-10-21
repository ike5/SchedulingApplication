package data;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The purpose of this class is to work with the first_level_divisions database table. There are only
 * 3 countries but there are 68 total divisions set up for this application. The functionality of this
 * class should be READ ONLY.
 */
public class DBDivisions {

    /**
     * Gets all divisions provided a countryId.
     *
     * @return A ResultSet object containing all listed Division_ID, Divisions related to the Country ID.
     */
    public static ObservableList<Division> getDivisions(int countryId) {
        String sql = "SELECT client_schedule.first_level_divisions.Division_ID,\n" +
                "       client_schedule.first_level_divisions.Division,\n" +
                "       client_schedule.countries.Country_ID,\n" +
                "       client_schedule.countries.Country\n" +
                "FROM countries, first_level_divisions\n" +
                "WHERE (client_schedule.first_level_divisions.COUNTRY_ID = client_schedule.countries.Country_ID)\n" +
                "AND (client_schedule.countries.Country_ID = ?);";
        ObservableList<Division> divisionObservableList = FXCollections.observableArrayList();
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
        String sql = "SELECT client_schedule.first_level_divisions.Division_ID,\n" +
                "       client_schedule.first_level_divisions.Division,\n" +
                "       client_schedule.countries.Country_ID,\n" +
                "       client_schedule.countries.Country\n" +
                "FROM countries, first_level_divisions\n" +
                "WHERE (client_schedule.first_level_divisions.COUNTRY_ID = client_schedule.countries.Country_ID);";
        ObservableList<Division> divisionObservableList = FXCollections.observableArrayList();
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

    /**
     * Gets a single COUNTRY_ID provided a Division_ID
     *
     * @param divisionId The division ID provided
     * @return Returns an integer representing the country id or -1 if invalid division id provided.
     */
    public static int getCountryId(int divisionId) {
        String sql = "SELECT COUNTRY_ID FROM first_level_divisions WHERE Division_ID = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionId);

            ResultSet resultSet = ps.executeQuery();

            resultSet.next();
            return resultSet.getInt("COUNTRY_ID");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1; // if unsuccessful
    }

    /**
     * Create a Division object. Creating a Division object also creates a Country object. Note that first_level_divisions
     * has a constraint on countries in the ERD model. We will want to make sure that all CRUD operations follow this
     * constraint with countries.
     *
     * @param divisionId
     * @return
     */
    public static Division getDivision(int divisionId) {
        String sql = "SELECT client_schedule.first_level_divisions.Division_ID,\n" +
                "       client_schedule.first_level_divisions.Division,\n" +
                "       client_schedule.countries.Country_ID,\n" +
                "       client_schedule.countries.Country\n" +
                "FROM countries, first_level_divisions\n" +
                "WHERE (client_schedule.first_level_divisions.COUNTRY_ID = client_schedule.countries.Country_ID)\n" +
                "AND (client_schedule.first_level_divisions.Division_ID = ?);";
        Division division = null;
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionId);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                division = new Division(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return division;
    }
}
