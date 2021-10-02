package model;


// This is an object that has many different methods for queries

import utils.JDBC;
import utils.QueryInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query implements QueryInterface{

    @Override
    public ResultSet getResultSet(String sql) {
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
