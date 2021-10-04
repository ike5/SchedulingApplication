package test;

import utils.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AnotherTest {
        QueryStatements query = (sql, resultSetIterator) -> {
            List<String> list = new ArrayList<>();
            try {

                JDBC.openConnection();

                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(rs.getString(resultSetIterator.iterator().next().toString()));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return list;
        };
}

class Thing {
    public static void main(String[] args) {

        String sql = "SELECT * FROM customers";
        ResultSetIterator resultSetIterator = new ResultSetIterator("Address", "Phone", "Customer_Name");
        AnotherTest anotherTest = new AnotherTest();

        List<String> myListOfQueryObjects = anotherTest.query.whileLoop(sql, resultSetIterator);

        System.out.println(myListOfQueryObjects);




    }
}

