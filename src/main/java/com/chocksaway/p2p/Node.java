package com.chocksaway.p2p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public final class Node {
    private final String hostname;
    private final String name;
    private final int port;

    private final List<String> messages;
    private static final Logger logger = LogManager.getLogger(Node.class);

    public Node(String name, int port) {
        this.hostname = "127.0.0.1";
        this.name = name;
        this.port = port;
        this.messages = new ArrayList<>();
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
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    try (Socket clientSocket = serverSocket.accept();
                         BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                        String message = reader.readLine();
                        messages.add(message);
                        System.out.printf("[%s:%d] Received: %s%n", name, port, message);
                    }
                }
            } catch (IOException e) {
                logger.error("Error {}", e.getMessage());
            }
        }).start();
    }

    public int getMessageCount() {
        return messages.size();
    }
}