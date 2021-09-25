package test;

import java.util.ListResourceBundle;

public class RB_fr_CA extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"mykey", new StringBuilder("This is the stringbuilder I got from the key!")}
        };
    }
}
