package model;

public class Country {
    private int id;
    private String name;

    public Country(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Country(String name, int divisionId){
        this.name = name;
        this.id = divisionId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
