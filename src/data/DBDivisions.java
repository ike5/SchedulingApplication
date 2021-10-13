package data;

//TODO - Add CRUD functionality
// Read

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import model.Country;
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
     * Gets all divisions provides a countryId.
     *
     * @return A ResultSet object containing all listed Division_ID, Divisions related to the Country ID.
     */
    public static ObservableList<Division> getDivisions(int countryId) {
        String sql = "SELECT Division_ID, Division FROM first_level_divisions WHERE COUNTRY_ID = " + countryId;
        ObservableList<Division> divisionObservableList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Division division = new Division(resultSet.getInt("Division_ID"), resultSet.getString("Division"));
                divisionObservableList.add(division);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionObservableList;
    }


    public static ObservableList<Division> getAllFirstLevelDivisions() {
        String sql = "select * from first_level_divisions";
        ObservableList<Division> divisionObservableList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Division division = new Division(resultSet.getInt("Division_ID"), resultSet.getString("Division"));
                divisionObservableList.add(division);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
        String sql = "SELECT COUNTRY_ID FROM first_level_divisions WHERE Division_ID = " + divisionId;
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();
            return resultSet.getInt("COUNTRY_ID");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1; // if unsuccessful
    }

    public static Division getDivision(int divisionId) {
        String sql = "SELECT " +
                "client_schedule.first_level_divisions.Division_ID, " +
                "client_schedule.first_level_divisions.Division " +
                "FROM first_level_divisions " +
                "WHERE Division_ID = " + divisionId;
        Division division = null;
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                division = new Division(resultSet.getInt("Division_ID"), resultSet.getString("Division"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return division;
    }

}
