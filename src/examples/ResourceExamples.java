package examples;

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

    public static void main(String[] args) {
        choosingResourceBundle();
    }


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

    private static void defaultLocale() {
        // store locale so can put it back at end
        Locale initial = Locale.getDefault();
        System.out.println(initial);

        // set locale to Germany
        Locale.setDefault(Locale.GERMANY);
        System.out.println(Locale.getDefault());

        // put original locale back
        Locale.setDefault(initial);
        System.out.println(Locale.getDefault());

        /*
        Output:

        en_US
        de_DE
        en_US
         */
    }

    private static void choosingResourceBundle() {
        /*
        Methods:
            ResourceBundle.getBundle(baseName)
                Above overloaded class: ResourceBundle.getBundle(baseName, Locale.getDefault())
            ResourceBundle.getBundle(baseName, locale)
         */

//        Locale locale = new Locale("fr", "CA");
//        ResourceBundle rb = ResourceBundle.getBundle("RB", locale);
//        System.out.println(rb.getObject("mykey"));

        Locale locale = new Locale("en", "UK");
        ResourceBundle rb = ResourceBundle.getBundle("RB", locale);
        System.out.println(rb.getString("ride.in") + " " + rb.getString("elevator"));

        /*
        Java looks for, in order:

        RB_fr_CA.java         // exactly what we asked for
        RB_fr_CA.properties

        RB_fr.java            // couldn't find exactly what we asked for
        RB_fr.properties      // now trying just requested language

        RB_en_US.java         // couldn't find French
        RB_en_US.properties   // now trying default Locale

        RB_en.java            // couldn't find full default Locale country
        RB_en.properties      // now trying default Locale language

        RB.java               // couldn't find anything any matching Locale,
        RB.properties         // now trying default bundle
         */
    }
}




