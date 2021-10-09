package model;

public class Country {
    private final int countryId;
    private final String name;

    public Country(int countryId, String name){
        this.countryId = countryId;
        this.name = name;
    }

//    public Country(String name, int divisionId){
//        this.name = name;
//        this.id = divisionId;
//    }



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
