package com.chocksaway.p2p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Objects;

public final class Link implements ILink, Serializable {
    private static final Logger logger = LogManager.getLogger(Link.class);
    private final INode from;
    private final INode to;

    public Link(INode from, INode to) {
        this.from = from;
        this.to = to;
    }

    public void addToFromRouter() {
        this.from.getRouter().addLink(this);
    }

    public void sendMessage(Object message) {
        try (Socket socket = new Socket(to.getHostname(), to.getPort());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            outputStream.writeObject(message);
            logger.info("Message {} sent from {}:{} to {}:{}: {}", message.getClass(), from.getName(), from.getPort(), to.getName(), to.getPort(), message);
        } catch (IOException e) {
            logger.error("Error sending message {} {}", e.getMessage(), e);
        }
    }

    public INode from() {
        return from;
    }

    public INode to() {
        return to;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Link) obj;
        return Objects.equals(this.from, that.from) &&
                Objects.equals(this.to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "Link[" +
                "from=" + from + ", " +
                "to=" + to + ']';
    }

}