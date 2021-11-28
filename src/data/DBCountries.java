package data;

import model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


/**
 * This class doesn't have any constraints.
 */
public class DBCountries {

    /**
     * Returns the ResultSet object of Countries. Since countries doesn't have a constraint, it is okay to implement
     * this method.
     *
     * @return ResultSet object
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

    /**
     * Returns an ObservableList<Country> object. Since the countries table does not have any constraints,
     * the only fields retrieved from the table are Country_ID and the Country (name).
     *
     * @return ObservableList<Country> object
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> countryObservableList = FXCollections.observableArrayList();
        try {
            // Get the ResultSet object from helper method
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
}