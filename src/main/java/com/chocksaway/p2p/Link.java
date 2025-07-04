package com.chocksaway.p2p;

import java.util.logging.Logger;

public class Link {
    final Node from;
    final Node to;

    private static final Logger logger = Logger.getLogger(Link.class.getName());

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
        this.from.removeMessage(message);
        this.to.addMessage(message);
        logger.info(String.format("Transferring message from %s to %s: %s", this.from.name(), this.to.name(), message));
    }
}
