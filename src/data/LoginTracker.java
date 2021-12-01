package data;

import model.LogType;

import java.io.*;
import java.nio.file.*;

import java.util.List;

/**
 * This class provides READ and WRITE functions for making log entries.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public class LoginTracker {

    /**
     * Logs login attempts. Appends individual login attempts to any designated file.
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
}
