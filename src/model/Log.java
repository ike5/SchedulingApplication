package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Log implements Serializable {
    private static final long serialVersionID = 1L;

    private User user;
    private Timestamp timestamp;
    private boolean isSuccessful;

    public Log(User user, Timestamp timestamp, boolean isSuccessful) {
        this.user = user;
        this.timestamp = timestamp;
        this.isSuccessful = isSuccessful;
    }

    public User getUser() {
        return user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    @Override
    public String toString() {
        return "Log{" +
                "user=" + user +
                ", timestamp=" + timestamp +
                ", isSuccessful=" + isSuccessful +
                '}';
    }
}
