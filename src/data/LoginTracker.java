package data;

import model.LogType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoginTracker {

    // Create lines in the log with appropriate messages
    // Read lines in the log

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

        try(var writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)){
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

    public static void main(String[] args) {
        addToLog(Path.of("src/data/login.log"), LogType.ALL, "S: Test");
    }
}
