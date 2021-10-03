package controller;

import utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static int instances;

    public Test() {
        this("This is instance #: ");
    }

    public Test(String str) {
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

}

class HelloWorldAnonymousClass {
    interface HelloWorld {
        public void greet();

        public void greetSomeone(String someone);
    }

    interface MakeAQuery {
        public void getResultSet();

        public <T> T processResultSet(ResultSet resultSet) throws SQLException;
    }

    public void makeItHappen() {
        class A implements MakeAQuery {
            String sql;

            A(String sql){
                this.sql = sql;
            }

            @Override
            public void getResultSet() {
                try {
                    PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                    processResultSet(ps.executeQuery());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            @Override
            public <T> T processResultSet(ResultSet resultSet) throws SQLException {
                while (resultSet.next()) {
                    System.out.println(resultSet.getRow());
                }
                return null;
            }
        }
        MakeAQuery customerQuery = new A("SELECT * FROM customers");
        MakeAQuery countryQuery = new MakeAQuery (){
            String sql = "SELECT * FROM countries";

            @Override
            public void getResultSet() {
                try {
                    PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                    processResultSet(ps.executeQuery());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            @Override
            public <T> T processResultSet(ResultSet resultSet) throws SQLException {
                String str = null;
                if (resultSet.next()) {
                    str = resultSet.getString(1);
                }
                return (T)str;
            }
        };
        MakeAQuery userQuery = new MakeAQuery() {
            String sql = "SELECT * FROM user";

            @Override
            public void getResultSet() {
                try {
                    PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                    processResultSet(ps.executeQuery());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            @Override
            public <T> T processResultSet(ResultSet resultSet) throws SQLException {
                List<String> myList = new ArrayList<>();

                while (resultSet.next()) {
                    System.out.println("Another result set!");
                    myList.add(resultSet.getString(1));
                }
                return (T) myList;
            }
        };

        customerQuery.getResultSet();
        countryQuery.getResultSet();
        userQuery.getResultSet();
    }

    public void sayHello() {
        class EnglishGreeting implements HelloWorld {
            String name = "world";

            @Override
            public void greet() {
                greetSomeone("world");
            }

            @Override
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Hello " + name);
            }
        }
        HelloWorld englishGreeting = new EnglishGreeting();
        HelloWorld frenchGreeting = new HelloWorld() {
            String name = "tout le monde";

            @Override
            public void greet() {
                greetSomeone("tout le monde");
            }

            @Override
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Salut " + name);
            }
        };
        HelloWorld spanishGreeting = new HelloWorld() {
            String name = "mundo";

            @Override
            public void greet() {
                greetSomeone("mundo");
            }

            @Override
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Hola, " + name);
            }
        };

        englishGreeting.greet();
        frenchGreeting.greet();
        spanishGreeting.greet();
    }

    public static void main(String[] args) {
        HelloWorldAnonymousClass myApp = new HelloWorldAnonymousClass();
        myApp.sayHello();
        myApp.makeItHappen();
    }
}
