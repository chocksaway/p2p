package com.chocksaway.p2p;

public class Link {
    final Node from;
    final Node to;

    public Link(Node from, Node to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return this.from.name();
    }

    public String getTo() {
        return this.to.name();
    }

    public void talk(String message) {
        System.out.printf("[%s] %s %s%n", this.from.name(), this.to.name(), message);
    }
}
