package com.chocksaway.p2p;

import com.chocksaway.p2p.route.Router;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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

        var linka = new Link(node1, node2);
        var linkb = new Link(node1, node3);
        var router = new Router();
        router.addLink(linka);
        router.addLink(linkb);

        assertTrue(router.linkExists(linka));
        assertTrue(router.linkExists(linkb));
        node1.stop();
        node2.stop();
        node3.stop();
    }

    @Test
    public void testRouterLinkMessage() throws InterruptedException {
        var node1 = new Node("node1", 8001);
        var node2 = new Node("node2", 8002);
        var node3 = new Node("node3", 8003);

        node1.start();
        node2.start();
        node3.start();

        var link = new Link(node1, node2);
        var link2 = new Link(node2, node3);
        var router = new Router();
        router.addLink(link);
        router.addLink(link2);

        node1.addRouter(router);
        node2.addRouter(router);

        link.sendMessage(link);
        link.sendMessage(link2);

        Thread.sleep(2000);

        assertEquals(2, node2.getLinks());
    }
}
