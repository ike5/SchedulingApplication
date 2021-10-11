package data;

//TODO - Add CRUD functionality
// Read

import model.Country;

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
    public static ResultSet getDivisions(int countryId) {
        String sql = "SELECT Division_ID, Division FROM first_level_divisions WHERE COUNTRY_ID = " + countryId;
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null; // if unsuccessful
    }

    public static ResultSet getAllFirstLevelDivisions() {
        String sql = "select * from first_level_divisions";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null; // if unsuccessful
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

}
