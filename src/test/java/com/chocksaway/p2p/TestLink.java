package com.chocksaway.p2p;

import com.chocksaway.p2p.route.Router;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestLink {
    @Test
    public void testLinkHasAFromAndTo() {
        var src = new Node("node1", 8000);
        var dest = new Node("node2", 8001);

        var link = new Link(src, dest);

        var router = new Router();
        router.addLink(link);
        src.addRouter(router);

        assertTrue(src.getRouter().linkExists(link));
    }

    @Test
    public void sendMessageOnLink() throws InterruptedException {
        var node1 = new Node("node1", 8000);
        var node2 = new Node("node2", 8001);
        var link = new Link(node1, node2);

        var router = new Router();
        router.addLink(link);
        node1.addRouter(router);

        node1.start();
        node2.start();

        final String message = "this is a sample message";



        assertTrue(router.send(link, message));

        Thread.sleep(2000); // wait for messages to be sent

        // explicit call to getName which includes the peer name
        assertEquals(1, node2.findNumberOfMessages(node2.getName()));
    }
}
