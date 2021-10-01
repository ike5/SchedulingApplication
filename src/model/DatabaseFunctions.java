package model;

import utils.DatabaseInterface;
import utils.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseFunctions {

    public ResultSet makeQuery(String sql) {
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public <T> T iterateResultSet(ResultSet rs, T t) throws SQLException {
        Iterable iterable;
        while (rs.next()) {
            iterable = (Iterable) t;
        }
        return t;
    }
}
