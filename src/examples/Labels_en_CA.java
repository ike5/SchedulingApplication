package examples;

import java.util.ListResourceBundle;

public class Labels_en_CA extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"hello", new StringBuilder("from Java")}
        };
    }
}
