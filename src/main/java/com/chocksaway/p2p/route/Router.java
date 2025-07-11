package com.chocksaway.p2p.route;

import com.chocksaway.p2p.Link;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Router {
    private final List<Link> links = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(Router.class);

    public void addLink(Link link) {
        if (link.from() == null || link.to() == null) {
            throw new NullPointerException("Source or destination is null");
        }
        links.add(link);

        logger.info("Add link: {}", link);
        send(link, "link added" + link);
    }

    public boolean send(Link link, String message) {
        if (linkExists(link)) {
            link.sendMessage(message);
            return true;
        }
        return false;
    }

    public boolean linkExists(Link link) {
        return links.stream()
                .anyMatch(each -> each.from().getName().equals(link.from().getName()) &&
                        each.to().getName().equals(link.to().getName()));
    }
}