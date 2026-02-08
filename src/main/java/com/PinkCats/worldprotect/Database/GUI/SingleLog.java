package com.PinkCats.worldprotect.Database.GUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SingleLog {

    private static final String DEFAULT_LOG_PATH = "WorldProtect/Log/Running.log";
    private static final long MAX_FILE_SIZE = 4194304L;

    // 成员变量
    private File logFile;
    private BufferedWriter writer;
    private String lastLogMessage = "";
    private static final java.util.Map<String, SingleLog> INSTANCE_CACHE = new java.util.HashMap<>();


    private SingleLog(String filePath) {
        this.logFile = new File(filePath);
        File parentDir = this.logFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean dirCreated = parentDir.mkdirs();
            if (dirCreated) {
                System.out.println("Log parent directory created: " + parentDir.getAbsolutePath());
            }
        }

        initWriter();
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }


    private void initWriter() {
        try {
            this.writer = new BufferedWriter(new FileWriter(this.logFile, true));
        } catch (IOException e) {
            System.err.println("Failed to initialize log writer for path: " + this.logFile.getAbsolutePath());
            e.printStackTrace();
        }
    }

    public static SingleLog getInstance(String... customPath) {
        String targetPath = (customPath != null && customPath.length > 0 && customPath[0] != null)
                ? customPath[0]
                : DEFAULT_LOG_PATH;
        return INSTANCE_CACHE.computeIfAbsent(targetPath, SingleLog::new);
    }

    private String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public void log(String message) {
        if (message.equals(this.lastLogMessage))
            return;

        if (this.writer == null) {
            System.err.println("Log writer not initialized, skip logging: " + message);
            return;
        }

        try {
            if (this.logFile.length() >= MAX_FILE_SIZE) {
                archiveLogFile();
            }

            String logContent = getCurrentTimestamp() + " " + message;
            this.writer.write(logContent);
            this.writer.newLine();
            this.writer.flush();
            this.lastLogMessage = message;

        } catch (IOException e) {
            System.err.println("Failed to write log message: " + message);
            e.printStackTrace();
        }
    }

    private void archiveLogFile() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String originalName = this.logFile.getName();
        String archivedName = originalName.replace(".log", "_" + timestamp + ".log");
        File archivedFile = new File(this.logFile.getParent(), archivedName);

        try {
            this.writer.close();
            boolean archived = this.logFile.renameTo(archivedFile);
            if (!archived) {
                System.err.println("Failed to archive log file: " + this.logFile.getAbsolutePath()
                        + " → " + archivedFile.getAbsolutePath());
            }
            initWriter();

        } catch (IOException e) {
            System.err.println("Error during log archive: " + e.getMessage());
            e.printStackTrace();
            initWriter();
        }
    }

    public void close() {
        if (this.writer != null) {
            try {
                this.writer.flush();
                this.writer.close();
                System.out.println("Log writer closed: " + this.logFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to close log writer: " + this.logFile.getAbsolutePath());
                e.printStackTrace();
            } finally {
                this.writer = null;
            }
        }
    }

    public void reopen() {
        if (this.writer == null) {
            initWriter();
        }
    }

    public static SingleLog WP_MES = SingleLog.getInstance("WorldProtect/Log/Running.log");


    public static void main(String[] args) {
        WP_MES.log("Default path log: This is a test message.");
        WP_MES.close();
    }
}