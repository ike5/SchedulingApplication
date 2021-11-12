package data;

import model.LogMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LoginTracker {

    void readFileLinesStartsWith(Path path, LogMessage logMessage) {
        // good for memory because uses a Stream
        try (var s = Files.lines(path)) {
            s.filter(f -> f.startsWith(logMessage.name())) // searches log lines with WARN
                    .map(f -> f.substring(logMessage.name().length()))
                    .forEach(System.out::println);
        } catch (IOException e){
            System.err.println("Something went wrong");
        }
    }
}
