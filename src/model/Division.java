package model;

/**
 * The type Division.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public class Division {
    private final int divisionId;
    private final String divisionName;
    private final Country country;

    /**
     * Instantiates a new Division.
     *
     * @param divisionId   the division id
     * @param divisionName the division name
     * @param countryId    the country id
     * @param countryName  the country name
     */
    public Division(int divisionId, String divisionName, int countryId, String countryName) {
        this.divisionName = divisionName;
        this.divisionId = divisionId;
        this.country = new Country(countryId, countryName);
    }

    /**
     * Instantiates a new Division.
     *
     * @param divisionId   the division id
     * @param divisionName the division name
     * @param country      the country
     */
    public Division(int divisionId, String divisionName, Country country) {
        this(divisionId, divisionName, country.getCountryId(), country.getName());
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
     * Gets division name.
     *
     * @return the division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return divisionName;
    }
}
