package model;


// This is an object that has many different methods for queries

import utils.JDBC;
import utils.QueryMaker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query{
    public ResultSet basicQuery(QueryMaker queryMaker){
        try{
            String sql = queryMaker.getSql();
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void printResultSetContents(ResultSet resultSet) throws SQLException {
        while (resultSet.next()){
            System.out.println(resultSet.getRow());
        }
    }
}
