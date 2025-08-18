package com.chocksaway.p2p;

import com.chocksaway.p2p.route.Router;
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
import java.util.Objects;

public final class Node implements Serializable {
    private final String hostname;
    private String name;
    private final int port;
    private final List<String> messages;
    private transient Router router;

    private volatile boolean running = false;

    private final transient Sinks.Many<String> messageSink = Sinks.many().multicast().onBackpressureBuffer();

    private static final Logger logger = LogManager.getLogger(Node.class);

    public Node(String name, int port) {
        this.hostname = "127.0.0.1";
        this.name = name;
        this.port = port;
        this.messages = new ArrayList<>();
        this.router = null;
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
            if (received instanceof String message) {
                addMessage(message);
                logger.info("[{}:{}] Received: {}", name, port, message);
            } else if (received instanceof Link link) {
                if (!Objects.equals(link.to().name, name)) {
                    logger.info("Forwarding link from {} to {}", name, link.to().name);
                    link.sendMessage(link);
                }
            } else {
                logger.warn("[{}:{}] Received unknown object type: {}", name, port, received.getClass().getName());
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

    public int getLinks() {
        return this.router.getLinks();
    }

    public void addRouter(Router router) {
        this.router = router;
    }

    public Router getRouter() {
        return this.router;
    }

    public int findNumberOfMessages(String name) {
        return this.messages.size();
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

    public void addLink(Link link) {
        if (this.router == null) {
            this.router = new Router();
        }
        this.router.addLink(link);
    }

    public boolean send(Link link, String message) {
        if (this.router == null) {
            this.router = new Router();
        }
        return this.router.send(link, message);
    }
}