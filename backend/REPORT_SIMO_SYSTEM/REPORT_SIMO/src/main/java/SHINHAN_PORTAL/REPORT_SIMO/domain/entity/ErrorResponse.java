package SHINHAN_PORTAL.REPORT_SIMO.domain.entity;

import java.time.LocalDateTime;

public class ErrorResponse {

    private String message;
    private String errorCode;
    private LocalDateTime timestamp;

    // No-argument constructor
    public ErrorResponse() {
    }

    // All-argument constructor
    public ErrorResponse(String message, String errorCode, LocalDateTime timestamp) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
    }

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Getter for errorCode
    public String getErrorCode() {
        return errorCode;
    }

    // Getter for timestamp
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setter for message.
    public void setMessage(String message) {
        this.message = message;
    }

    // Setter for errorCode.
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    // Setter for timestamp.
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}