package utils;

import model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DBCountries {
    public static ObservableList<Country> getAllCountries() {
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

    /**
     * Could implement some sort of factory to reduce the repetitive code?
     *
     * @param divisionId
     * @return
     */
    public static String getCountryFromDivisionId(int divisionId) {
        String countryName = null;
        try {
            String sql = "SELECT countries.Country FROM countries INNER JOIN first_level_divisions " +
                    "on countries.Country_ID = first_level_divisions.COUNTRY_ID;";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                switch (rs.getString(1)){
                    case "U.S":
                        System.out.println("U.S");
                        countryName = rs.getString(1);
                        break;
                    case "UK":
                        System.out.println("UK");
                        countryName = rs.getString(1);
                        break;
                    case "Canada":
                        System.out.println("Canada");
                        countryName = rs.getString(1);
                        break;
                    default:
                        System.out.println("Something went wrong");
                        countryName = null;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return countryName;
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