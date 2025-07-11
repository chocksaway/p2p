package com.chocksaway.p2p;

import com.chocksaway.p2p.route.Router;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestRouter {
    @Test
    public void testRouter() {
        var node1 = new Node("node1", 8000);
        var node2 = new Node("node2", 8001);
        var node3 = new Node("node3", 8002);

        node1.start();
        node2.start();
        node3.start();

        // Wait for servers to start
        try {
            Thread.sleep(2000); // Ensure servers are ready
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        var linka = new Link(node1, node2);
        var linkb = new Link(node1, node3);
        var router = new Router();
        router.addLink(linka);
        router.addLink(linkb);

        assertTrue(router.linkExists(linka));
        assertTrue(router.linkExists(linkb));
    }
}
