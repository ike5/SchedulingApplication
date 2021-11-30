package model;

import data.DBCountries;
import data.DBDivisions;
import main.Main;

/**
 * Rules: A customer cannot be made without a first_level_division and country
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public class Customer {
    private final int id;
    private String name;
    private String address;
    private String phone;
    private String postalCode;
    private int divisionId;
    private int countryId;
    private String countryName;
    private final Country country;    // Required constraint
    private final Division division;  // Required constraint
    private final User created_by;
    private User last_updated_by;

    public Customer(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone, Division division) {
        this.id = customerId; // This can throw an error in the database if not normalized
        this.name = customerName;
        this.address = customerAddress;
        this.postalCode = customerPostalCode;
        this.phone = customerPhone;
        this.country = division.getCountry();
        this.countryName = division.getCountry().getName();
        this.countryId = division.getCountry().getCountryId();
        this.division = division;
        this.divisionId = division.getDivisionId();
        this.created_by = Main.user;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Country getCountry() {
        return country;
    }

    public Division getDivision() {
        return division;
    }

    public User getCreated_by() {
        return created_by;
    }

    public User getLast_updated_by() {
        return last_updated_by;
    }

    public void setLast_updated_by(User last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    @Override
    public String toString() {
        return name;
    }
}
