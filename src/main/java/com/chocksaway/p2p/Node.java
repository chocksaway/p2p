package com.chocksaway.p2p;

import com.chocksaway.p2p.message.AckMessage;
import com.chocksaway.p2p.message.SimpleMessage;
import com.chocksaway.p2p.route.BaseNode;
import com.chocksaway.p2p.route.Router;
import com.chocksaway.p2p.utils.NetworkUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public final class Node implements Serializable {
    private final String hostname;
    private final String name;
    private final int port;
    private final BaseNode baseNode;
    private final List<String> messages;
    private final List<String> ackMessages = new ArrayList<>();
    private transient Router router;

    private volatile boolean running = false;

    private final transient Sinks.Many<String> messageSink = Sinks.many().multicast().onBackpressureBuffer();

    private static final Logger logger = LogManager.getLogger(Node.class);

    public Node(String name, int port) {
        this.hostname = "127.0.0.1";
        this.name = name;
        this.port = port;
        this.baseNode = new BaseNode(this.hostname, this.name, this.port);
        this.messages = new ArrayList<>();
        this.router = new Router(this.name);
    }

    private void addMessage(String message) {
        messages.add(message);
        messageSink.tryEmitNext(message);
    }

    public Flux<String> getMessageFlux() {
        return messageSink.asFlux();
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
        logger.info("Starting: {}", this.name);
        new Thread(this::runServer).start();
    }

    private void runServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            running = true;
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

            switch (received) {
                case String message -> process(message);
                case SimpleMessage simpleMessage -> process(simpleMessage);
                case AckMessage ackMessage -> process(ackMessage);
                case null -> logger.warn("[{}:{}] Received null object", name, port);
                default ->
                        logger.warn("[{}:{}] Received unknown object type: {}", name, port, received.getClass().getName());
            }
        } catch (IOException e) {
            logger.error("Error handling client connection: {}", e.getMessage());
        }
    }


    private void process(String message) {
        addMessage(message);
        logger.info("[{}:{}] Received: {}", name, port, message);
    }

    private void process(SimpleMessage simpleMessage) {
        if (simpleMessage.getDestination().equals(this.name)) {
            logger.info("[{}:{}] Received message for self: {}{}", name, port, simpleMessage.getMessage(), simpleMessage.getPath());
            addMessage(simpleMessage.getMessage());
            // Send an acknowledgment back to the sender
            var ackMessage = new AckMessage(simpleMessage.getPath().getFirst(), "Ack from " + this.name, simpleMessage.getPath(), this.getBaseNode());
            ackMessage.buildLink(simpleMessage.getPath().getLast(), this.baseNode);
            router.send(ackMessage);
        } else {
            logger.info("[{}:{}] Forwarding message to: {}", name, port, simpleMessage.getDestination());
            simpleMessage.addToPath(this.baseNode);
            router.send(simpleMessage);
        }
    }

    private void process(AckMessage ackMessage) {
        logger.info("[{}:{}] Received ack message: {}", name, port, ackMessage.getMessage());

        if (ackMessage.getDestination().getName().equals(this.name)) {
            logger.info("[{}:{}] Ack is for self: {}", name, port, ackMessage.getMessage());
            this.ackMessages.add(ackMessage.getMessage());
            this.router.storePath(ackMessage.getPath(), ackMessage.getSource());
        } else {
            logger.info("[{}:{}] Forwarding ack to: {}", name, port, ackMessage.getDestination());
            router.send(ackMessage);
        }
    }



    public int getAckMessages() {
        return this.ackMessages.size();
    }

    public int getLinks() {
        return this.router.getLinks();
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        logger.info("Stopping: {}", this.name);
        messages.clear();
        try {
            // Close the server socket if it was opened
            new ServerSocket(port).close();
        } catch (IOException e) {
            logger.error("Error closing server socket on port {}: {}", port, e.getMessage());
        }
    }

    public void send(SimpleMessage message) {
        if (this.router == null) {
            this.router = new Router(this.name);
        }
        this.router.send(message);
    }

    public Router getRouter() {
        return this.router;
    }

    public int getMessages() {
        return this.messages.size();
    }

    public BaseNode getBaseNode() {
        return this.baseNode;
    }

    public List<BaseNode> getShortestPath(String destination) {
        return this.router.getShortestPath(destination);
    }

    public boolean connectToNode(Node node) {
        return NetworkUtils.canConnect(node.hostname, node.port, 2000);
    }
}