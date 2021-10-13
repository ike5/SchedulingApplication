package model;

public class Division {
    private int divisionId;
    private String divisionName;

    public Division(int divisionId, String divisionName) {
        this.divisionName = divisionName;
        this.divisionId = divisionId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    @Override
    public String toString() {
        return "#" + this.divisionId + " " + this.divisionName;
    }
}
