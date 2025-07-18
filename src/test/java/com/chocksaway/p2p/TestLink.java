package com.chocksaway.p2p;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestLink {
    @Test
    public void testLinkHasAFromAndTo() {
        var src = new Node("node1", 8000);
        var dest = new Node("node2", 8001);

        var link = new Link(src, dest);

        var peer = new Peer("peer");
        peer.addLink(link);

        assertTrue(peer.getRouter().linkExists(link));
    }

    @Test
    public void sendMessageOnLink() throws InterruptedException {
        var node1 = new Node("node1", 8000);
        var node2 = new Node("node2", 8001);
        var link = new Link(node1, node2);

        var peer1 = new Peer("peer1");
        var peer2 = new Peer("peer2");

        peer1.start(node1);
        peer2.start(node2);

        peer1.addLink(link);

        final String message = "this is a sample message";

        assertTrue(peer1.send(link, message));

        Thread.sleep(2000); // wait for messages to be sent

        // explicit call to getName which includes the peer name
        assertEquals(1, peer2.findNumberOfMessages(node2.getName()));
    }
}
