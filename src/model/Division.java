package model;

public class Division {
    private String divisionName;
    private int divisionId;

    public Division(int divisionId, String divisionName) {
        this.divisionName = divisionName;
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    @Override
    public String toString() {
        return "#" + this.divisionId + " " + this.divisionName;
    }
}
