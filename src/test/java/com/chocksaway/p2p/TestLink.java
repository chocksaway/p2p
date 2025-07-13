package com.chocksaway.p2p;

import com.chocksaway.p2p.route.Router;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestLink {




    @Test
    public void testLinkHasAFromAndTo() {
        var node1 = new Node("127.0.0.1", 8000);
        var node2 = new Node("127.0.0.1", 8001);

        var link = new Link(node1, node2);
        var router = new Router();

        router.addLink(link);

        assertTrue(router.linkExists(link));
    }

    @Test
    public void sendMessageOnLink() {
        var node1 = new Node("127.0.0.1", 8000);
        var node2 = new Node("127.0.0.1", 8001);

        var link = new Link(node1, node2);

        node1.start();
        node2.start();

        var router = new Router();
        router.addLink(link);

        final String message = "this is a sample message";

        assertTrue(router.send(link, message));
        assertEquals(0, node1.getMessageCount());
        assertEquals(2, node2.getMessageCount());
    }
}
