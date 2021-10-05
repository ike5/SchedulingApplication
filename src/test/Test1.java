package test;

//public class Test1 {
//    public static void main(String[] args) {
//        SearchApp searchApp = new SearchApp();
//        searchApp.myCamera(new CameraAcceptor() {
//            // Since myCamera() is a function, it needs to do something with the object it received
//            @Override
//            public boolean choose(Camera c) {
//                return c.equals("Nikon");
//            }
//        });
//    }
//}
//
//class SearchApp {
//    // myCamera() is a function that takes in a CameraAcceptor object
//    public Camera myCamera(CameraAcceptor acc){
//        return acc.choose();
//    };
//}
//
//interface CameraAcceptor {
//    boolean choose(Camera c);
//}
//
//class Camera {
//    String name;
//    boolean isIlc;
//    double price;
//
//    public Camera() {
//        this("Default Canon");
//    }
//
//    public Camera(String name) {
//        this(name, true, 1999.99);
//    }
//
//    public Camera(String name, boolean isIlc, double price) {
//        this.name = name;
//        this.isIlc = isIlc;
//        this.price = price;
//    }
//
//    public boolean isIlc() {
//        return isIlc;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public String getName() {
//        return name;
//    }
//}
