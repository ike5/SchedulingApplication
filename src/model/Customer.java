package model;

import utils.DBCountries;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String country;
    private int divisionID;

    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionId;
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

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
