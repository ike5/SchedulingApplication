package model;

import main.Main;

/**
 * The type Customer.
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

    /**
     * Instantiates a new Customer.
     *
     * @param customerId         the customer id
     * @param customerName       the customer name
     * @param customerAddress    the customer address
     * @param customerPostalCode the customer postal code
     * @param customerPhone      the customer phone
     * @param division           the division
     */
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets postal code.
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets division id.
     *
     * @return the division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets division id.
     *
     * @param divisionId the division id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets country id.
     *
     * @return the country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets country id.
     *
     * @param countryId the country id
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets country name.
     *
     * @return the country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets country name.
     *
     * @param countryName the country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Gets division.
     *
     * @return the division
     */
    public Division getDivision() {
        return division;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public User getCreated_by() {
        return created_by;
    }

    /**
     * Gets last updated by.
     *
     * @return the last updated by
     */
    public User getLast_updated_by() {
        return last_updated_by;
    }

    /**
     * Sets last updated by.
     *
     * @param last_updated_by the last updated by
     */
    public void setLast_updated_by(User last_updated_by) {
        this.last_updated_by = last_updated_by;
    }

    @Override
    public String toString() {
        return name;
    }
}
