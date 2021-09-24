package model;

import utils.DBCountries;

public class DivisionId {
    private int divisionId;
    private String countryName;

    public DivisionId(int divisionId){
        this.divisionId = divisionId;
        this.countryName = DBCountries.getCountryFromDivisionId(divisionId);
    }

    public int getDivisionId() {
        return divisionId;
    }

    public String getCountryName() {
        return countryName;
    }
}
