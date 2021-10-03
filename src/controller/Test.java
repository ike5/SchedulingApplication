package controller;

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
}
