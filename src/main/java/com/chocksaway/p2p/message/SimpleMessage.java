package com.chocksaway.p2p.message;

import java.io.Serializable;

public class SimpleMessage implements Serializable {
    private final String destination;
    private final String message;

    public SimpleMessage(String destination, String message) {
        this.destination = destination;
        this.message = message;
    }

    public String getDestination() {
        return destination;
    }

    public String getMessage() {
        return this.message;
    }
}
