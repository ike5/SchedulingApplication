package test;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceExamples {
    // Naming rules:
    // 1) Files must end in .properties
    // 2) End of name before .properties must be a string with an _ underscore declaring
    // the Locale the file represents

    // Examples
    // MyApp_en.properties --> bundle name: MyApp
    // MyApp_fr.properties --> bundle name: MyApp
    // MyApp_fr_CA.properties --> bundle name: MyApp

    // Steps for using a ResourceBundle
    // 1) Obtain Locale
    // 2) Get ResourceBundle
    // 3) Looking up value from resource bundle


    public static void createObject() {
        /*
        Methods:
            getBundle() // looks in classpath for bundles
         */

        // Use any one of these Locales
        Locale locale1 = new Locale("en"); // language - English
        Locale locale2 = new Locale("en", "CA"); // language and country - Canadian English
        Locale locale3 = Locale.CANADA; // constant for common locales

        // Create resource bundle
        // Find bundle name
        // Pass those values to a factory to create resource bundle

        ResourceBundle rb = ResourceBundle.getBundle("Labels", locale1);

        // Get resource bundles
        rb.getString("hello");
    }
}
