package com.example.tasktrackerb7.exceptions;

public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException() {
        super();
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}
