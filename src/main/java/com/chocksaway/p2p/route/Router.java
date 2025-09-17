package com.chocksaway.p2p.route;

import com.chocksaway.p2p.Link;
import com.chocksaway.p2p.message.AckMessage;
import com.chocksaway.p2p.message.SimpleMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Supplier;

public class Router  {
    private final String name;
    private final List<Link> links = new ArrayList<>();

    private final Map<String, List<List<BaseNode>>> paths;



    private static final Logger logger = LogManager.getLogger(Router.class);

    public Router(String name) {
        this.name = name;
        this.paths = new HashMap<>();
    }

    public void addLink(Link link) {
        if (link.from() == null || link.to() == null) {
            throw new NullPointerException("Source or destination is null");
        }
        links.add(link);
    }

    public void send(SimpleMessage message) {
        for (Link link : links) {
            link.sendMessage(message);
        }
    }

    public void send(AckMessage message) {
        if (!findLinkInPath(message)) {
            var destination = links.stream()
                    .filter(link -> message.getDestination().getName().equals(link.to().getName()))
                    .findFirst();

            destination.ifPresent(link -> link.sendMessage(message));

            var link = message.getLink();
            link.sendMessage(message);
        }
    }

    private boolean findLinkInPath(AckMessage message) {
        return message.getPath().stream()
                .filter(each -> each.getName().equals(this.name))
                .findFirst()
                .map(node -> {
                    var index = message.getPath().indexOf(node);
                    if (index > 0) {
                        var from = message.getPath().get(index - 1);
                        var to = message.getPath().get(index);
                        message.buildLink(from, to);
                        message.getLink().sendMessage(message);
                    }
                    return true;
                })
                .orElseGet(() -> {
                    logger.warn("Node {} not in path {}", this.name, message.getPath());
                    return false;
                });
    }

    public int getLinks() {
        return this.links.size();
    }

    public void storePath(List<BaseNode> path, BaseNode source) {
        var pathsFromSource = this.paths.get(source.getName());
        if (pathsFromSource != null) {

            pathsFromSource.add(path);
        } else {

            this.paths.put(source.getName(), new ArrayList<>(List.of(path)));
        }

    }

    public Map<String, List<List<BaseNode>>> getPaths() {
        return this.paths;
    }
}