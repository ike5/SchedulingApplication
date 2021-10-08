package data;

import model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

interface CountryDAO {
    // CREATE, READ, UPDATE, DELETE

    public ObservableList<Country> getAllCountries(); // read

    public Country getCountry(int countryId); // read

    public Country editCountry(Country country); // update

    public boolean deleteCountry(Country country); // delete
}

public class DBCountries extends JDBC implements CountryDAO {


    public ObservableList<Country> getAllCountries() {
        ObservableList<Country> countrieslist = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country C = new Country(countryId, countryName);
                countrieslist.add(C);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return countrieslist;
    }

    @Override
    public Country getCountry(int countryId) {
        return null;
    }

    @Override
    public Country editCountry(Country country) {
        return null;
    }

    @Override
    public boolean deleteCountry(Country country) {
        return false;
    }

    /**
     * Could implement some sort of factory to reduce the repetitive code?
     *
     * @param divisionId
     * @return
     */
    public static Country getCountryFromDivisionId(int divisionId) {
        Country country = null;
        try {
            String sql = "USE client_schedule; SELECT * FROM (SELECT countries.Country, client_schedule.first_level_divisions.Division_ID " +
                    "FROM countries INNER JOIN first_level_divisions ON countries.Country_ID = client_schedule.first_level_divisions.COUNTRY_ID) " +
                    "as CDI WHERE CDI.Division_ID = " + divisionId;

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                country = new Country(rs.getString("Country"), rs.getInt("Division_ID"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return country;
    }

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