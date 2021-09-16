package model;

import data.DBCountries;
import javafx.collections.ObservableList;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String country;

    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country = DBCountries.getCountryFromDivisionId(divisionId);
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

    public String getPostal() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }
}
