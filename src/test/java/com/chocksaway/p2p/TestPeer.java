package com.chocksaway.p2p;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPeer {
    @Test
    public void sendMessagesToPeer() throws InterruptedException {
        var node1 = new Node("node1", 8000);
        var node2 = new Node("node2", 8001);
        var node3 = new Node("node3", 8002);
        var link1 = new Link(node1, node2);
        var link2 = new Link(node1, node3);

        var peer1 = new Peer("peer1");
        var peer2 = new Peer("peer2");

        peer1.start(node1);
        peer2.start(node2);
        peer2.start(node3);

        peer1.addLink(link1);
        peer1.addLink(link2);

        final String message = "this is a sample message";

        assertTrue(peer1.send(link1, message));
        assertTrue(peer1.send(link2, message));

        Thread.sleep(2000); // wait for messages to be sent

        assertEquals(1, peer2.findNumberOfMessages(node2.getName()));
        assertEquals(1, peer2.findNumberOfMessages(node3.getName()));
    }

    @Test
    public void sendMessageFromToAndToFrom() throws InterruptedException {
        var node1 = new Node("node1", 8000);
        var node2 = new Node("node2", 8001);
        var link1 = new Link(node1, node2);
        var link2 = new Link(node2, node1);

        var peer1 = new Peer("peer1");

        peer1.start(node1);
        peer1.start(node2);

        peer1.addLink(link1);
        peer1.addLink(link2);

        final String message = "this is a sample message";

        assertTrue(peer1.send(link1, message));
        assertTrue(peer1.send(link2, message));

        Thread.sleep(4000); // wait for messages to be sent

        assertEquals(1, peer1.findNumberOfMessages(node1.getName()));
        assertEquals(1, peer1.findNumberOfMessages(node2.getName()));
    }
}