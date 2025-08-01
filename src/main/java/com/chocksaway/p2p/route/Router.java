package com.chocksaway.p2p.route;

import com.chocksaway.p2p.Link;
import com.chocksaway.p2p.exception.LinkExistsException;
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
        } else if (linkExists(link)) {
            throw new LinkExistsException(link + " already exists");
        }
        links.add(link);

        logger.info("Add link: {}", link);
    }

    public boolean send(Link link, String message) {
        if (linkExists(link)) {
            link.sendMessage(message);
            if (true) { //todo: add a config option to control this
                links.stream()
                        .filter(each -> each != link)
                        .forEach(each -> each.sendMessage(message));
            }
            return true;
        }
        return false;
    }

    public boolean linkExists(Link link) {
        return links.stream()
                .anyMatch(
                        each ->
                                each.from().getName().equals(link.from().getName()) &&
                                    each.to().getName().equals(link.to().getName())
                );
    }

    public int getLinks() {
        return this.links.size();
    }
}