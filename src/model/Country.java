package model;
/**
 * @author Ike Maldonado
 * @version 1.0
 */
public class Country {
    private final int countryId;
    private final String name;

    public Country(int countryId, String name){
        this.countryId = countryId;
        this.name = name;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
