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

class HelloWorldAnonymousClass{
    interface HelloWorld{
        public void greet();
        public void greetSomeone(String someone);
    }

    public void sayHello(){
        class EnglishGreeting implements HelloWorld{
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
    }
}
