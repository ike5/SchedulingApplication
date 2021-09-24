package test;

import java.io.*;
import java.util.Properties;

public class MySystemProperties {
    /*
    Methods:
        String getProperty(String key)
        void list(PrintStream out)
        void load(InputStream inStream)
        Object setProperty(String key, String value)
        void store(OutputStream out, String headerComment)
     */

    public static void viewProperties() {
        Properties p = System.getProperties();
        p.setProperty("myProp", "myValue");
        p.list(System.out);
    }

    public static void main(String[] args) {
        openUpFileCreated();
    }

    public static void workingWithProperties() {
        Properties p = new Properties();
        p.setProperty("k1", "v1");
        p.setProperty("k2", "v2");
        p.list(System.out);
        try {
            // creates or replaces file
            FileOutputStream out = new FileOutputStream("myProps1.props");
            p.store(out, "test-comment"); // adds header comment
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("exc 1");
        }
    }

    public static void openUpFileCreated() {
        Properties p2 = new Properties();
        try {
            FileInputStream in = new FileInputStream("myProps1.props");
            p2.load(in);
            p2.list(System.out);
            p2.setProperty("newProp", "newData");
            p2.list(System.out);
            FileOutputStream out = new FileOutputStream("myProps2.props");
            p2.store(out, "myUpdate");
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
