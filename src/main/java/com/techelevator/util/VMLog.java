package com.techelevator.util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VMLog {
    private static PrintWriter writer = null;

    public static void log(String message) {
        if(writer == null) {
            try {
                String logFilename = "logs/log.txt";
                File logFile = new File(logFilename);
                try {
                    if(!logFile.exists())
                        logFile.createNewFile();
                } catch(IOException e) {
                    throw new VMLogException("Run Time Error: " + e.getMessage());
                }

                writer = new PrintWriter(new FileOutputStream(logFilename));
            } catch(FileNotFoundException e) {
                throw new VMLogException("Run Time Error: " + e.getMessage());
            }
        }

        try {
            writer.print(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss a"))) ;
            writer.println(" " + message);
            writer.flush();
        } catch (VMLogException e) {
            throw new VMLogException("Run Time Error: " + e.getMessage());
        }
    }
}
