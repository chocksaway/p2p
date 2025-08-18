package com.chocksaway.p2p;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestLink {
    @Test
    public void sendMessageOnLink() throws InterruptedException {
        var node1 = new Node("node1", 8000);
        var node2 = new Node("node2", 8001);
        var link = new Link(node1, node2);

        node1.start();
        node2.start();

        node1.addLink(link);

        final String message = "this is a sample message";

        assertTrue(node1.send(link, message));

        Thread.sleep(2000); // wait for messages to be sent

        // explicit call to getName which includes the peer name
        assertEquals(1, node2.findNumberOfMessages(node2.getName()));
    }
}
