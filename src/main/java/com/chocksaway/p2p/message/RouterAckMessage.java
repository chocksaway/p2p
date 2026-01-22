package com.chocksaway.p2p.message;

import com.chocksaway.p2p.Link;

import java.io.Serializable;

public class RouterAckMessage implements IMessage, Serializable {
    private Link link;

    public void setLink(Link link) {
        this.link = link;
    }

    public Link getLink() {
        return this.link;
    }
}