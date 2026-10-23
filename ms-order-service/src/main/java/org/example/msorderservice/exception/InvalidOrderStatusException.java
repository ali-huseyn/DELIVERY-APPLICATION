package org.example.msorderservice.exception;

public class InvalidOrderStatusException extends RuntimeException {
    public InvalidOrderStatusException(String message) { super(message);
    }
}
