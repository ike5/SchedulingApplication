package model;

import data.DBCountries;
import data.DBDivisions;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String postalCode;
    private int divisionId;
    private int countryId;
    private String countryName;
    private Country country;

    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId) {
        this.id = id; // This can throw an error in the database if not normalized
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.country = DBDivisions.getCountry(divisionId);
        this.countryName = country.getName();
        this.countryId = country.getCountryId();
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
