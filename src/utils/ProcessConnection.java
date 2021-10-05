package utils;

import java.sql.Connection;

public class ProcessConnection implements OpenConnectionInterface, CloseConnectionInterface, GetConnectionInterface{

    @Override
    public void closeConnection() {

    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public Connection openConnection() {
        return null;
    }
}
