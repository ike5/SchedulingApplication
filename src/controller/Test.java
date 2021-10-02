package controller;

import model.Query;
import model.QueryTesterClass;
import utils.QueryInterface;

import java.sql.ResultSet;

public class Test {
    public static int instances;

    public Test() {
        this("This is instance #: ");
    }

    public Test(String str){
        incrementInstances();
        System.out.println(str + " [" + instances + "]");
    }

    /**
     * In order for an accurate number of instances to be recorded, you must
     * execute a new run of the JVM.
     */
    private static void incrementInstances() {
        instances++;
    }

    public static void main(String[] args) {
        String sql = "SELECT * FROM customers";
        QueryTesterClass q = new QueryTesterClass();

        QueryInterface queryInterface = q.processQuery(c -> {
        }

}
