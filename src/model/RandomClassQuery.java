package model;

import utils.JDBC;
import utils.QueryMaker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RandomClassQuery implements QueryMaker {
    private String sql;
    public RandomClassQuery(String sql){
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }


}
