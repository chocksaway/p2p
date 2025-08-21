package com.chocksaway.p2p.route;

import com.chocksaway.p2p.Link;
import com.chocksaway.p2p.message.SimpleMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Router  {
    private final String name;
    private final List<Link> links = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(Router.class);

    public Router(String name) {
        this.name = name;
    }

    public void addLink(Link link) {
        if (link.from() == null || link.to() == null) {
            throw new NullPointerException("Source or destination is null");
        }
        links.add(link);
    }

    public boolean send(SimpleMessage message) {
        for (Link link : links) {
            link.sendMessage(message);
        }
        return false;
    }

    public int getLinks() {
        return this.links.size();
    }
}