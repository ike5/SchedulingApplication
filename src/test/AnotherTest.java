package test;

import utils.FunctionalResultSetInterface;
import utils.JDBC;
import utils.ProcessQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnotherTest {
    public static void main(String[] args) {
        String sql = "SELECT * FROM customers";

        // sql, (send in the logic of the while loop)
        ProcessQuery.process(sql, x -> {
            System.out.println(x.getRow());
            System.out.println(x.getString(2));
        });

        ProcessQuery.process("SELECT * FROM first_level_divisions", new FunctionalResultSetInterface() {
            @Override
            public void whileLogic(ResultSet resultSet) throws SQLException {
                System.out.println(resultSet.getString(2));
            }
        });
    }


}



