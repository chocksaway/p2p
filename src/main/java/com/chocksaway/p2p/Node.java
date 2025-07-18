package com.chocksaway.p2p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public final class Node {
    private final String hostname;
    private String name;
    private final int port;
    private final List<String> messages;

    private static final Logger logger = LogManager.getLogger(Node.class);

    public Node(String name, int port) {
        this.hostname = "127.0.0.1";
        this.name = name;
        this.port = port;
        this.messages = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("Node{name='%s', hostname='%s', port=%d}", name, hostname, port);
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public String getHostname() {
        return hostname;
    }

    public void start() {
        new Thread(this::runServer).start();
    }

    private void runServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                handleClient(serverSocket);
            }
        } catch (IOException e) {
            logger.error("Error starting server on port {}: {}", port, e.getMessage());
        }
    }

    private void handleClient(ServerSocket serverSocket) {
        try (Socket clientSocket = serverSocket.accept();
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())) {
            Object received = null;
            try {
                received = inputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                logger.error("Error receiving data from server on port {}: {}", port, e.getMessage());
            }
            if (received instanceof String message) {
                messages.add(message);
                logger.info("[{}:{}] Received: {}", name, port, message);
            }
        } catch (IOException e) {
            logger.error("Error handling client connection: {}", e.getMessage());
        }
    }

    public int getMessageCount() {
        return messages.size();
    }

    public void setName(String name) {
        this.name = name;
    }
}