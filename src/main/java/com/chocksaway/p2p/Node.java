package com.chocksaway.p2p;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public final class Node {
    private final String name;
    private final List<String> messsages;

    private final int port;

    public Node(String name, int port) {
        this.name = name;
        this.port = port;
        this.messsages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    try (Socket clientSocket = serverSocket.accept();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                        String message = reader.readLine();
                        this.messsages.add(message);
                        System.out.printf("[%s %s] Received: %s%n", name,port, message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public int getMessageCount() {
        return messsages.size();
    }
}