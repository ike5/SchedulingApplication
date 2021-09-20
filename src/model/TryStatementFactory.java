package model;

import data.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Not sure what I'm doing here. I was hoping to make a Factory out of the
 * try and ResultSet programming. I really hate repeated code and want to minimize
 * it.
 *
 * @param <T>
 */
public class TryStatementFactory<T> {
    private String sql;
    private T whileLoop;
    private T returnType;

    public TryStatementFactory(String sql, T whileLoop, T returnType) {
        this.sql = sql;
        this.whileLoop = whileLoop;
        this.returnType = returnType;
    }

    public void tryFactory() {
        try {
            String sql = this.sql;

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
