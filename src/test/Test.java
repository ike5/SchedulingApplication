package test;

import data.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Test {
    public static int instances;

    public Test() {
        this("This is instance #: ");
    }

    public Test(String str) {
        incrementInstances();
        System.out.println(str + " [" + instances + "]");
    }

    public Test(Object obj) {
        incrementInstances();
        System.out.println(obj.toString() + " [" + instances + "]");
    }

    public static void changeLocale() {
        Locale.setDefault(new Locale("fr", "CA")); // Test to set default to French Canadian
    }

    /**
     * In order for an accurate number of instances to be recorded, you must
     * execute a new run of the JVM.
     */
    private static void incrementInstances() {
        instances++;
    }

}

