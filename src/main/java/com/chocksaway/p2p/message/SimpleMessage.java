package com.chocksaway.p2p.message;

import com.chocksaway.p2p.route.BaseNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SimpleMessage implements Serializable {
    private final String destination;
    private final String message;
    private final List<BaseNode> path = new ArrayList<>();

    public List<BaseNode> getPath() {
        return this.path;
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

    public void addToPath(BaseNode baseNode) {
        this.path.add(baseNode);
    }

    @Override
    public String toString() {
        return String.format("SimpleMessage{destination='%s', message='%s', path=%s}", destination, message, path);
    }
}
