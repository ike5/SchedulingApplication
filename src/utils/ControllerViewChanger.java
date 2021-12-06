package utils;

import model.View;

@FunctionalInterface
public interface ControllerViewChanger {
    public void switchView(View view);
}
