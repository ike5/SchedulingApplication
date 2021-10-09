package data;

import model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

//TODO
// - Add CRUD functionality
// - Remove extending JDBC

public class DBCountries extends JDBC {

    /**
     * Returns the ResultSet object of the SELECT * query from the countries database table.
     *
     * @return ResultSet object
     */
    public ResultSet getAllCountriesResultSet() {
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
     * Returns an ObservableList<Country> object of all countries listed in the countries database table.
     * listed in the countries database table.
     *
     * @return ObservableList<Country> object
     */
    public ObservableList<Country> getAllCountries() {
        ObservableList<Country> countryObservableList = FXCollections.observableArrayList();
        try {
            ResultSet rs = getAllCountriesResultSet();

            while (rs.next()) {
                Country C = new Country(rs.getInt("Country_ID"), rs.getString("Country"));
                countryObservableList.add(C);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return countryObservableList;
    }


    /**
     * Returns a Country object provided a country id.
     *
     * @param countryId
     * @return Country object
     */
    public Country getCountry(int countryId) {
        String sql = "SELECT Country_ID, Country FROM countries WHERE Country_ID = " + countryId;
        Country country;
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                country = new Country(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
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

//    /**
//     * Could implement some sort of factory to reduce the repetitive code?
//     *
//     * @param divisionId
//     * @return
//     */
//    public static Country getCountryFromDivisionId(int divisionId) {
//        Country country = null;
//        try {
//            String sql = "USE client_schedule; SELECT * FROM (SELECT countries.Country, client_schedule.first_level_divisions.Division_ID " +
//                    "FROM countries INNER JOIN first_level_divisions ON countries.Country_ID = client_schedule.first_level_divisions.COUNTRY_ID) " +
//                    "as CDI WHERE CDI.Division_ID = " + divisionId;
//
//            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
//
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                country = new Country(rs.getString("Country"), rs.getInt("Division_ID"));
//            }
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return country;
//    }

    //FIXME - flagged unused method

    /**
     * Not sure if this method is necessary. Flag for removal
     */
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
}