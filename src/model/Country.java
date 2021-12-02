package model;

/**
 * The type Country.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public class Country {
    private final int countryId;
    private final String name;

    /**
     * Instantiates a new Country.
     *
     * @param countryId the country id
     * @param name      the name
     */
    public Country(int countryId, String name){
        this.countryId = countryId;
        this.name = name;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
