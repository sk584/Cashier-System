package se.kth.iv1350.createpos.util;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class handles logging of exceptions and errors to a log file.
 * Each log entry includes the time, date, error message, and stack trace.
 */
public class FileLogger {
    private static final String LOG_FILE = "log.txt"; 
    private  PrintWriter logWriter;
    
     /**
     * Creates a new FileLogger that writes to the specified log file.
     * @throws IOException if the log file cannot be created or opened.
     */
    public FileLogger() throws IOException {
            logWriter = new PrintWriter(new FileWriter(LOG_FILE, true), true);
        }
    
    /**
     * Logs the details of an exception to the log file, including the time, date, error message, and stack trace.
     * @param e The exception to log.
     */
      public void log(Exception e) {
        logWriter.println("Time and Date of error:  " + java.time.LocalDateTime.now());
        logWriter.println("ERROR: " + e.getMessage());
        e.printStackTrace(logWriter); 
        logWriter.println("--------------------------------------------------");
        logWriter.flush(); 
        }
    }


