package model;

import data.DBDivisions;

public class Division {
    private final int divisionId;
    private final String divisionName;
    private final Country country;

    public Division(int divisionId, String divisionName, int countryId, String countryName) {
        this.divisionName = divisionName;
        this.divisionId = divisionId;
        this.country = new Country(countryId, countryName);
    }

    public Division(int divisionId, String divisionName, Country country){
        this(divisionId, divisionName, country.getCountryId(), country.getName());
    }

    public int getDivisionId() {
        return divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public Country getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Division{" +
                "divisionId=" + divisionId +
                ", divisionName='" + divisionName + '\'' +
                ", country=" + country +
                '}';
    }
}
