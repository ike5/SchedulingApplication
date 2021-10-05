package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface FunctionalResultSetInterface{
    void whileLogic(ResultSet resultSet) throws SQLException;
}