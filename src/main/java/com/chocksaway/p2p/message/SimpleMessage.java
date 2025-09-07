package com.chocksaway.p2p.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SimpleMessage implements Serializable {
    private final String destination;
    private final String message;
    private final List<String> path = new ArrayList<>();

    public String getPath() {
        return String.join("->", path);
    }

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

    public void addToPath(String name) {
        this.path.add(name);
    }
}
