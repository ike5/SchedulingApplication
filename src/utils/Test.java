package utils;

public class Test {
    public static void main(String[] args) {
        Duck duck = new Duck();

        test(x -> x.quack(), duck);
        test(x -> x.scream(), duck);
    }

    /**
     * This is the method that utilizes the functional interface in order to
     * do something on the Duck object.
     */
    public static void test(ControllerUtil c, Duck d) {
        System.out.println(c.doSomething(d));
    }
}

/**
 * This is the Duck object. It has various methods.
 */
class Duck {
    public String quack() {
        return "It Quacks!";
    }

    public String scream() {
        return "It's screaming!";
    }
}






