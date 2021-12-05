package utils;

import javafx.event.ActionEvent;
import model.View;

@FunctionalInterface
public interface ControllerViewChanger {
    public void switchView(View view);
}
