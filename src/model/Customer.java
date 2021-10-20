package model;

import data.DBCountries;
import data.DBDivisions;

/**
 * Rules: A customer cannot be made without a first_level_division and country
 */
public class Customer {
    private final int id;
    private final String name;
    private final String address;
    private final String phone;
    private final String postalCode;
    private final int divisionId;
    private final int countryId;
    private final String countryName;
    private final Country country;    // Required constraint
    private final Division division;  // Required constraint

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
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public Country getCountry() {
        return country;
    }

    public Division getDivision() {
        return division;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", phone='" + phone + '\'' +
                ", divisionId=" + divisionId +
                ", countryId=" + countryId +
                '}';
    }
}
