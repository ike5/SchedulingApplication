package data;

import javafx.util.Pair;
import main.Main;
import model.Log;
import model.LogType;
import model.User;
import test.Test;

import java.io.*;
import java.nio.file.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoginTracker {

    @Deprecated
    public static void readLogMessages(Path path, LogType logType) {
        // good for memory because uses a Stream
        try (var s = Files.lines(path)) {
            s.filter(f -> f.startsWith(logType.name())) // searches log lines with WARN
                    .map(f -> f.substring(logType.name().length()))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Something went wrong");
        }
    }

    /**
     * Adds new line to log starting each line with an identifying logType value.
     *
     * @param path       The path to the log file
     * @param logType    Identifies the login attempt as a SUCCESS or FAILURE
     * @param logMessage The log message should include the success, the username, and the datetime information
     */
    public static void addToLog(Path path, LogType logType, String logMessage) {
        try (var writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer
                    .append(logType.name())
                    .append("\t")
                    .append(logMessage);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Deprecated(since = "1", forRemoval = false)
    public static void readAllMessages(Path path) {
        try {
            final List<String> lines = Files.readAllLines(path.normalize());
            lines.forEach(System.out::println);
        } catch (IOException e) {
            //
        }
    }

    /**
     * This method counts the total number of lines in a file
     *
     * @param path the path of the file
     * @return an integer representing the total number of lines in a file.
     */
    public static Integer getNumberOfLogMessages(Path path) {
        Integer counter = 0;
        try {
            final List<String> lines = Files.readAllLines(path.normalize());
            for (String l : lines) {
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }

    @Deprecated(since = "1", forRemoval = false)
    public static void addToObjectLog(Pair<String, String> usernameAndPasswordReceived) {
        Log log = new Log(
                usernameAndPasswordReceived,
                Timestamp.valueOf(LocalDateTime.now()),
                (Main.user.isValidUsername() && Main.user.isValidPassword())
        );
    }

    @Deprecated(since = "1", forRemoval = false)
    public static void serializeLog(List<Log> logs, File file) throws IOException {
        try (var out = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(file)))) {
            for (Log log : logs) {
                out.writeObject(log);
            }
        }
    }

    @Deprecated(since = "1", forRemoval = false)
    public static List<Log> deserializeLog(File file) throws IOException {
        var logs = new ArrayList<Log>();
        try (var in = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(file)))) {
            while (true) {
                var object = in.readObject();
                if (object instanceof Log) {
                    logs.add((Log) object);
                }
            }
        } catch (EOFException e) {
            System.err.println("End of file reached in deserializeLog()");
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found exception: ");
        }
        return logs;
    }
}
