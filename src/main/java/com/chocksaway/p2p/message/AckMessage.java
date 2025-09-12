package com.chocksaway.p2p.message;

import com.chocksaway.p2p.Link;
import com.chocksaway.p2p.route.BaseNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AckMessage implements Serializable {
    private final BaseNode destination;
    private final String message;
    private Link link;
    private List<BaseNode> path = new ArrayList<>();

    public AckMessage(BaseNode destination, String message, List<BaseNode> path) {
        this.destination = destination;
        this.message = message;
        this.path = path;
        this.link = null;
    }

    public void buildLink(BaseNode from, BaseNode to) {
        var toNode = from.build();
        var fromNode = to.build();

        this.link = new Link(fromNode, toNode);
    }

    public Link getLink() {
        return this.link;
    }

    public String getMessage() {
        return this.message;
    }

    public BaseNode getDestination() {
        return this.destination;
    }

    public List<BaseNode> getPath() {
        return this.path;
    }
}
