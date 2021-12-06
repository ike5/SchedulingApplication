package data;

import model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides READ functionality to the countries database table.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public class DBCountries {

    /**
     * Returns a Country ObservableList object. Since the countries table does not have any constraints,
     * the only fields retrieved from the table are Country_ID and the Country (name).
     *
     * @return Country ObservableList object
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> countryObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = getAllCountriesResultSet();

            while (resultSet.next()) {
                Country country = new Country(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );
                countryObservableList.add(country);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return countryObservableList;
    }

    /**
     * Helper method that returns the ResultSet object of countries.
     *
     * @return ResultSet object of countries. If no countries, returns null.
     */
    public static ResultSet getAllCountriesResultSet() {
        try {
            String sql = "SELECT * FROM countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            return ps.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}