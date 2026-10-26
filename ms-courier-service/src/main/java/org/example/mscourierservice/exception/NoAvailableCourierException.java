package org.example.mscourierservice.exception;

public class NoAvailableCourierException extends RuntimeException {
    public NoAvailableCourierException(String message) {
        super(message);
    }
}
