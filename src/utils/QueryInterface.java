package utils;


import java.sql.ResultSet;

public interface QueryInterface {
    public ResultSet getResultSet(String sql);
}