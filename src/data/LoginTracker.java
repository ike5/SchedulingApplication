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
import java.util.Optional;

public class LoginTracker {

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

    public static void addToLog(Path path, LogType logType, String logMessage) {
        try (var writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            writer
                    .append(logType.name())
                    .append("\t")
                    .append(logMessage);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readAllMessages(Path path) {
        try {
            final List<String> lines = Files.readAllLines(path.normalize());
            lines.forEach(System.out::println);
        } catch (IOException e) {
            //
        }
    }

    public static void addToObjectLog(Pair<String, String> usernameAndPasswordReceived) {
        Log log = new Log(
                usernameAndPasswordReceived,
                Timestamp.valueOf(LocalDateTime.now()),
                (Main.user.isValidUsername() && Main.user.isValidPassword())
        );
    }

    public static void serializeLog(List<Log> logs, File file) throws IOException {
        try (var out = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(file)))) {
            for (Log log : logs) {
                out.writeObject(log);
                new Test();
            }
        }
    }

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
            System.err.println("End of file reached");
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found exception: ");
        }
        return logs;
    }
}
