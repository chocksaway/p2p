package com.chocksaway.p2p.message;

import com.chocksaway.p2p.Link;
import com.chocksaway.p2p.route.BaseNode;

import java.io.Serializable;

public class RouterAckMessage implements Serializable {
    private final BaseNode baseNode;
    private Link link;

    public RouterAckMessage(BaseNode baseNode) {
        this.baseNode = baseNode;
        this.link = null;
    }

    public void addLink(Link link) {
        this.link = link;
    }

    public Link getLink() {
        return this.link;
    }
}