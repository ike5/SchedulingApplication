package model;

import javafx.util.Pair;

import java.io.Serializable;
import java.sql.Timestamp;

public class Log implements Serializable {
    private static final long serialVersionUID = -8038516943656318192L;
    private Pair<String, String> usernameAndPasswordReceived;
    private String user;
    private Timestamp timestamp;
    private Boolean isSuccessful;

    public Log(Pair<String, String> usernameAndPasswordReceived, Timestamp timestamp, Boolean isSuccessful) {
        this.usernameAndPasswordReceived = usernameAndPasswordReceived;
        this.timestamp = timestamp;
        this.isSuccessful = isSuccessful;
        this.user = usernameAndPasswordReceived.getKey();
    }

    public String getUser() {
        return user;
    }

    public Pair<String, String> getUsernameAndPasswordReceived() {
        return usernameAndPasswordReceived;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Boolean getIsSuccessful() {
        return isSuccessful;
    }

    @Override
    public String toString() {
        return "Log{" +
                "usernameAndPasswordReceived=" + usernameAndPasswordReceived +
                ", timestamp=" + timestamp +
                ", isSuccessful=" + isSuccessful +
                '}';
    }
}
