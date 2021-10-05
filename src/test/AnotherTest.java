package test;

import utils.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnotherTest {

    public static void main(String[] args) {
        new ProcessResultSet(new GetResultSet().getResultSet("SELECT * FROM customers"));

    }

    // need a method that consumes a QueryStatements Object
}

class GetResultSet implements GetResultSetInterface{
    @Override
    public ResultSet getResultSet(String sql) {
        try {
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}

class ProcessResultSet {
    ProcessResultSet(ResultSet resultSet){
        try {
            while(resultSet.next()){
                System.out.println(resultSet.getRow());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

@FunctionalInterface
interface GetResultSetInterface{
    // this is the expression contract
    ResultSet getResultSet(String sql);
}

@FunctionalInterface
interface ProcessResultSetInterface{
    // processes the expression above
    void processResultSet(GetResultSet getResultSet);
}



