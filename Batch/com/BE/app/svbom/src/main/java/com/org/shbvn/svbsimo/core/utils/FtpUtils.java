package com.org.shbvn.svbsimo.core.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FtpUtils {
    private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;
    public static void downloadFile(String server, int port, String user, String pass, String remoteDir, String localDir, String fileName) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            connectAndLogin(ftpClient, server, port, user, pass);
            configureFtpClient(ftpClient);

            String remotePath = remoteDir + "/" + fileName;
            Path localPath = Paths.get(localDir, fileName);
            if (!Files.exists(localPath.getParent())) {
                Files.createDirectories(localPath.getParent());
            }

            downloadFileFromFtp(ftpClient, remotePath, localPath);
            logger.info("File downloaded successfully: {}", localPath);
        } catch (IOException e) {
            logger.error("Error downloading file from FTP server", e);
            throw e;
        } finally {
            disconnectQuitely(ftpClient);
        }
    }
    public static void downloadAllFiles(String server, int port, String user, String pass,
                                    String remoteDir, String localDir) throws IOException {
    FTPClient ftpClient = new FTPClient();

    try {
        connectAndLogin(ftpClient, server, port, user, pass);
        configureFtpClient(ftpClient);

        // Lấy danh sách file từ thư mục remote
        FTPFile[] files = ftpClient.listFiles(remoteDir);
        if (files == null || files.length == 0) {
            logger.warn("No files found in FTP directory: {}", remoteDir);
            return;
        }

        // Đảm bảo thư mục local tồn tại
        Path localPathDir = Paths.get(localDir);
        if (!Files.exists(localPathDir)) {
            Files.createDirectories(localPathDir);
        }

        // Lặp qua từng file và tải về
        for (FTPFile file : files) {
            if (!file.isFile()) continue; // Bỏ qua thư mục

            String fileName = file.getName();
            String remotePath = remoteDir + "/" + fileName;
            Path localPath = localPathDir.resolve(fileName);

            logger.info("Downloading file: {}", fileName);
            try (OutputStream outputStream = Files.newOutputStream(localPath)) {
                boolean success = ftpClient.retrieveFile(remotePath, outputStream);
                if (success) {
                    logger.info("Downloaded: {}", fileName);
                } else {
                    logger.warn("Failed to download: {}", fileName);
                }
            }
        }

    } catch (IOException e) {
        logger.error("Error downloading files from FTP", e);
        throw e;
    } finally {
        disconnectQuitely(ftpClient);
    }
}
public static void downloadAllFilesAndArchive(String server, int port, String username, String password,
                                                  String remoteDir, String localBaseDir, String logFolder) throws IOException {
        FTPClient ftpClient = new FTPClient();

        try {
            connectAndLogin(ftpClient, server, port, username, password);
            configureFtpClient(ftpClient);

            FTPFile[] files = ftpClient.listFiles(remoteDir);
            if (files == null || files.length == 0) {
                logger.warn("No files found in remote FTP directory: {}", remoteDir);
                return;
            }

            Path archiveDir = createArchiveDirectory(logFolder);
            Path logFile = archiveDir.resolve("log.txt");
            String dateFolder = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String ftpArchiveDir = remoteDir + "/" + dateFolder;
            try (BufferedWriter logWriter = Files.newBufferedWriter(logFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                for (FTPFile file : files) {
                    processFileDownload(ftpClient, remoteDir, archiveDir, file, logWriter, localBaseDir,logFolder, ftpArchiveDir);
                }
            }

        } finally {
            disconnectQuitely(ftpClient);
        }
    }
    private static void connectAndLogin(FTPClient ftpClient, String server, int port, String user, String pass) throws IOException{
        ftpClient.connect(server, port);
        if (!ftpClient.login(user, pass)) {
            ftpClient.logout();
            throw new IOException("Failed to login to FTP server with user: " + user);
        }
       
    }
    private static void configureFtpClient(FTPClient ftpClient) throws IOException{
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        logger.info("Connected and logged in to FTP server: {}");
    }
    private static void downloadFileFromFtp(FTPClient ftpClient, String remotePath, Path localPath) throws IOException{
        // Implement the logic to download file from FTP server
        Files.createDirectories(localPath.getParent());
        try(OutputStream outputStream = Files.newOutputStream(localPath)) {
            boolean success = ftpClient.retrieveFile(remotePath, outputStream);
            if (!success) {
                throw new IOException("Failed to download file: " + remotePath);
            }
        }
    }
    private static Path createArchiveDirectory(String baseDir) throws IOException {
        String dateFolder = LocalDate.now().format(DATE_FORMATTER);
        Path archiveDir = Paths.get(baseDir, dateFolder);
        Files.createDirectories(archiveDir);
        return archiveDir;
    }

    private static void processFileDownload(FTPClient ftpClient, String remoteDir, Path archiveDir,
                                            FTPFile file, BufferedWriter logWriter, String tempDownloadDir, String logFolder, String ftpArchiveDir) {

        if (!file.isFile()) return;

        String originalFileName = file.getName();
        String fileName = originalFileName;

        if (fileName.startsWith("SIMO122")) {
            fileName = "INST_API1_22_WDR" + fileName.substring("SIMO122".length());
        }
        String remotePath = remoteDir + "/" + originalFileName;
        logger.info("remotePath: {}", remotePath);
        Path tempPath = Paths.get(tempDownloadDir, fileName);
        logger.info("tempPath: {}", tempPath);
        Path finalPath = archiveDir.resolve(fileName);
        logger.info("Processing file: {}", fileName);

        try (OutputStream os = Files.newOutputStream(tempPath)) {
            boolean success = ftpClient.retrieveFile(remotePath, os);

            if (success) {
                Files.copy(tempPath, finalPath, StandardCopyOption.REPLACE_EXISTING);
                writeLog(logWriter, "SUCCESS", fileName, null);
                logger.info("Downloaded and archived: {}", fileName);
                // Ensure the remote archive directory exists
                ensureRemoteDirectoryExists(ftpClient, ftpArchiveDir);
  
                // Move file on FTP to archive folder (remote move)
                String ftpArchivePath = ftpArchiveDir + "/" + originalFileName;
                boolean moved = ftpClient.rename(remotePath, ftpArchivePath);
                if (moved) {
                    logger.info("Moved file to FTP archive folder: {}", ftpArchivePath);
                    writeLog(logWriter, "SUCCESS", fileName, null);
                } else {   
                    logger.warn("Downloaded but FAILED to move file on FTP: {}", fileName);
                    writeLog(logWriter, "PARTIAL_SUCCESS", fileName, "Downloaded but FTP move failed");
                }
            } else {
                writeLog(logWriter, "FAILED", fileName, null);
                logger.warn("Failed to download: {}", fileName);
            }

        } catch (IOException e) {
            writeLog(logWriter, "ERROR", fileName, e.getMessage());
            logger.error("Exception downloading file: {}", fileName, e);
        }
    }

    private static void writeLog(BufferedWriter writer, String status, String fileName, String errorMessage) {
        try {
            String timestamp = java.time.LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    
            String operation = "FTP_DOWNLOAD_AND_ARCHIVE";
            String jobId = "SIMO_BATCH_JOB"; // you can make this dynamic if needed
            String hostname = java.net.InetAddress.getLocalHost().getHostName();
    
            StringBuilder logEntry = new StringBuilder();
            logEntry.append(String.format("%s | %s | %s | %s | %s", 
                timestamp, status, fileName, operation, hostname));
    
            if (errorMessage != null && !errorMessage.isBlank()) {
                logEntry.append(" | ").append(errorMessage.replaceAll("[\\r\\n]+", " "));
            }
    
            writer.write(logEntry.toString());
            writer.newLine();
            writer.flush(); // optional but safe
        } catch (IOException e) {
            logger.warn("Failed to write to log file for file: {}", fileName, e);
        }
    }

    private static void disconnectQuitely(FTPClient ftpClient) {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                logger.error("Error disconnecting FTP client", e);
            }
        }
    }
    private static void ensureRemoteDirectoryExists(FTPClient ftpClient, String remoteDirPath) throws IOException {
        String[] pathParts = remoteDirPath.split("/");
        String currentPath = "";
        for (String part : pathParts) {
            if (part.isEmpty()) continue;
            currentPath += "/" + part;
            if (!ftpClient.changeWorkingDirectory(currentPath)) {
                boolean created = ftpClient.makeDirectory(currentPath);
                if (created) {
                    logger.info("Created remote directory: {}", currentPath);
                } else {
                    logger.warn("Failed to create remote directory: {}", currentPath);
                }
            }
        }
    }
    
    
}
