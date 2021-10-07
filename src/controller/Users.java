package controller;

import javafx.fxml.Initializable;
import model.User;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class Users extends User implements Initializable  {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public ZonedDateTime startDateTime() {
        return null;
    }

    @Override
    public ZonedDateTime endDateTime() {
        return null;
    }

    @Override
    public Locale createDateTime() {
        return null;
    }

    @Override
    public Locale lastUpdateDateTime() {
        return null;
    }

    @Override
    public String createdBy(String name) {
        return null;
    }

    @Override
    public String lastUpdatedBy(String name) {
        return null;
    }

    //TODO allow the following
    // - change username
    // - change password
    // - update Person class values
}
