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

    /**
     * This method is unnecessary for CRUD constraint restrictions. Should be getting country id from a division object.
     *
     * @param countryId
     * @return Country object
     */
    @Deprecated
    public Country getCountryFromCountryId(int countryId) {
        String sql = "SELECT Country_ID, Country FROM countries WHERE Country_ID = ?";
        Country country = null;
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, countryId);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                country = new Country(resultSet.getInt(1), resultSet.getString(2));
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return country;
    }

    /**
     * Should not use this method since SQL database is READ ONLY for Country
     *
     * @param country
     * @return
     */
    @Deprecated
    public Country updateCountry(Country country) {
        return null;
    }

    /**
     * Should not use this method since SQL database is READ ONLY for Country
     *
     * @param country
     * @return
     */
    @Deprecated
    public boolean deleteCountry(Country country) {
        return false;
    }

    /**
     * Should not use this method since SQL database is READ ONLY for Country
     *
     * @param country
     * @return
     */
    @Deprecated
    public boolean createCountry(Country country) {
        return false;
    }

    /**
     * This method is unnecessary since every Division object should have a Country associated with it in order to
     * make CRUD operations possible.
     *
     * @param divisionId
     * @return
     */
    @Deprecated
    public static Country getCountryFromDivisionId(int divisionId) {
        Country country = null;
        try {
            String sql = "SELECT * FROM (SELECT countries.Country, client_schedule.first_level_divisions.Division_ID " +
                    "FROM countries INNER JOIN first_level_divisions ON countries.Country_ID = client_schedule.first_level_divisions.COUNTRY_ID) " +
                    "as CDI WHERE CDI.Division_ID = " + divisionId;

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                country = new Country(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                );
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return country;
    }

    /**
     * Not sure if this method is necessary. Flag for removal
     */
    @Deprecated
    public static void checkDateConversion() {
        System.out.println("CREATE DATE TEST");
        String sql = "SELECT Create_Date from countries";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This is an unnecessary method if you have a Division object at hand.
     *
     * @param divisionId
     * @return
     */
    @Deprecated
    public static Country getCountry(int divisionId) {
        String sql = "SELECT client_schedule.countries.Country_ID, Country, Division_ID " +
                "FROM countries " +
                "LEFT JOIN first_level_divisions fld ON countries.Country_ID = fld.COUNTRY_ID " +
                "WHERE Division_ID = " + divisionId;
        Country country = null;
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                country = new Country(resultSet.getInt("Country_ID"), resultSet.getString("Country"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return country;
    }
}