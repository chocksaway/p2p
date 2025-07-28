package com.chocksaway.p2p;

import org.junit.Test;

public class TestSubscriber {
    @Test
    public void sendMessageOnLink() throws InterruptedException {
        var node1 = new Node("node1", 8001);
        var node2 = new Node("node2", 8002);
        var node3 = new Node("node3", 8003);
        var node4 = new Node("node4", 8004);
        var node5 = new Node("node5", 8005);
        var node6 = new Node("node6", 8006);

        var link = new Link(node1, node2);
        var link3 = new Link(node3, node2);
        var link4 = new Link(node4, node2);
        var link5 = new Link(node5, node2);
        var link6 = new Link(node6, node2);

        var peer1 = new Peer("peer1");
        var peer3 = new Peer("peer3");
        var peer4 = new Peer("peer4");
        var peer5 = new Peer("peer5");
        var peer6 = new Peer("peer6");

        peer1.start(node1);
        peer3.start(node3);
        peer4.start(node4);
        peer5.start(node5);
        peer6.start(node6);

        peer1.addLink(link);
        peer3.addLink(link3);
        peer4.addLink(link4);
        peer5.addLink(link5);
        peer6.addLink(link6);


        final String message = "this is a sample message";

        peer1.send(link, message);
        peer3.send(link3, message);
        peer4.send(link4, message);
        peer5.send(link5, message);
        peer6.send(link6, message);

        Thread.sleep(2000);
    }
}
