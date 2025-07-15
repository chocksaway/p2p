package com.chocksaway.p2p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public record Link(Node from, Node to) {
    private static final Logger logger = LogManager.getLogger(Link.class);

    public void sendMessage(String message) {
        try (Socket socket = new Socket(to.getHostname(), to.getPort());
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            writer.println(message);
            logger.info("Message sent from {}:{} to {}:{}: {}%n", from.getName(), from.getPort(), to.getName(), to.getPort(), message);
        } catch (IOException e) {
            logger.error("Error sending message {} {}", e.getMessage(), e);
        }
    }
}