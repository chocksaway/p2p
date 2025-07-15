package com.chocksaway.p2p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public record Link(Node from, Node to) {
    private static final Logger logger = LogManager.getLogger(Link.class);

    public void sendMessage(Object message) {
        try (Socket socket = new Socket(to.getHostname(), to.getPort());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            outputStream.writeObject(message);
            logger.info("Message sent from {}:{} to {}:{}: {}", from.getName(), from.getPort(), to.getName(), to.getPort(), message);
        } catch (IOException e) {
            logger.error("Error sending message {} {}", e.getMessage(), e);
        }
    }
}