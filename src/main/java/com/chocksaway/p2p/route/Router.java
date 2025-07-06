package com.chocksaway.p2p.route;

import com.chocksaway.p2p.Link;

import java.util.ArrayList;
import java.util.List;

public class Router {
    private final List<Link> links = new ArrayList<>();

    public Router(Link link) {
        if (link.from() == null || link.to() == null) {
            throw new NullPointerException("src or destination is null");
        }

        links.add(link);
    }

    public boolean send(Link link, String message) {
        if (this.linkExists(link)) {
            link.sendMessage(message);

            return true;
        }

        return false;
    }

    public boolean linkExists(Link link) {
        return links.stream().anyMatch(each -> each.from().equals(link.from()) && each.to().equals(link.to()));
    }
}
