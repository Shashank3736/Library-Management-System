package com.tcs.Library.error;

public class NoCopyAvailableException extends RuntimeException {
    public NoCopyAvailableException(String message) {
        super(message);
    }
}
