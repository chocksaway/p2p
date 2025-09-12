package com.chocksaway.p2p.route;

import com.chocksaway.p2p.Node;

import java.io.Serializable;

public class BaseNode implements Serializable {
    protected final String hostname;
    protected final String name;
    protected final int port;

    public BaseNode(String hostname, String name, int port) {
        this.hostname = hostname;
        this.name = name;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public Node build() {
        return new Node(this.name, this.port);
    }

    @Override
    public String toString() {
        return String.format("BaseNode{name='%s', hostname='%s', port=%d}", name, hostname, port);
    }

}
