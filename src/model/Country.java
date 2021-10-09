package model;

public class Country {
    private final int countryId;
    private final String name;

    // country id should always pair with a name (and ideally never change)
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
