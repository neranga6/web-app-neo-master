package com.webapp.neo.exceptions;

import java.io.Serial;

public final class WriterException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;  // To ensure compatibility during serialization

    // Optionally add an error code or context information
    private final int errorCode;

    // Default constructor
    public WriterException() {
        super("An error occurred in the writer operation.");
        this.errorCode = 0;  // Default error code
    }

    // Constructor with a custom message
    public WriterException(String message) {
        super(message);
        this.errorCode = 0;  // Default error code
    }

    // Constructor with a custom message and error code
    public WriterException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    // Constructor with a cause (Throwable)
    public WriterException(Throwable cause) {
        super(cause);
        this.errorCode = 0;  // Default error code
    }

    // Constructor with a custom message, cause, and error code
    public WriterException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    // Constructor with a custom message, cause, and suppression settings
    public WriterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = 0;  // Default error code
    }

    // Getter for the error code
    public int getErrorCode() {
        return errorCode;
    }

    // Override toString() method to include error code in the message
    @Override
    public String toString() {
        return super.toString() + " [ErrorCode: " + errorCode + "]";
    }
}

