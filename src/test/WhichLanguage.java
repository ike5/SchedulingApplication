package test;

import java.util.Locale;
import java.util.ResourceBundle;

public class WhichLanguage {
    public static void main(String[] args) {
        Locale locale = new Locale(args[0]);
        ResourceBundle rb = ResourceBundle.getBundle("Labels", locale);
        System.out.println(rb.getString("hello"));


        /*
        Input:
        > java WhichLanguage.java en
        > java WhichLanguage.java fr

        Output:
        Hello Java!
        Bonjour Java!
         */
    }

    public static void mainDoesntWork(String[] args) {
        Locale locale = new Locale("en");
        ResourceBundle rb = ResourceBundle.getBundle("Labels", locale);
        System.out.println("hello");
    }
}
