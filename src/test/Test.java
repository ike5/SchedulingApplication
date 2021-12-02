package test;

import java.util.Locale;

/**
 * The type Test.
 */
public class Test {
    /**
     * The constant instances.
     */
    public static int instances;

    /**
     * Instantiates a new Test.
     */
    public Test() {
        this("This is instance #: ");
    }

    /**
     * Instantiates a new Test.
     *
     * @param str the str
     */
    public Test(String str) {
        incrementInstances();
        System.out.println(str + " [" + instances + "]");
    }

    /**
     * Instantiates a new Test.
     *
     * @param obj the obj
     */
    public Test(Object obj) {
        incrementInstances();
        System.out.println(obj.toString() + " [" + instances + "]");
    }

    /**
     * In order for an accurate number of instances to be recorded, you must
     * execute a new run of the JVM.
     */
    private static void incrementInstances() {
        instances++;
    }

    /**
     * Change locale.
     */
    public static void changeLocale() {
        Locale.setDefault(new Locale("fr", "CA")); // Test to set default to French Canadian
    }
}

