package com.chocksaway.p2p.exception;

public class LinkExistsException extends RuntimeException {
    public LinkExistsException(String message) {
        super(message);
    }

    public LinkExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
