package com.tcs.Library.error;

public class DuplicateBookBorrowException extends RuntimeException {
    public DuplicateBookBorrowException(String message) {
        super(message);
    }
}
