package com.chocksaway.p2p;

import com.chocksaway.p2p.message.Message;

import com.chocksaway.p2p.route.BaseNode;
import org.junit.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestMostEfficientRoute {
    private static final Logger logger = LogManager.getLogger(TestMostEfficientRoute.class);

    @Test
    public void testSendAndReturnAckMessage() throws InterruptedException {
        var node1 = new Node("node1", 8011);
        var node2 = new Node("node2", 8012);
        var node3 = new Node("node3", 8013);
        var node4 = new Node("node4", 8014);
        var node5 = new Node("node5", 8015);

        node1.start();
        node2.start();
        node3.start();
        node4.start();
        node5.start();

        var link12 = new Link(node1, node2);
        var link13 = new Link(node1, node3);
        var link24 = new Link(node2, node4);
        var link34 = new Link(node3, node5);
        var link54 = new Link(node5, node4);

        link12.addToFromRouter();
        link13.addToFromRouter();
        link24.addToFromRouter();
        link34.addToFromRouter();
        link54.addToFromRouter();

        var message = "Hello, Node 4!";
        var simpleMessage = new Message("node4", message);
        simpleMessage.addToPath(new BaseNode(node1.getName(), node1.getName(), node1.getPort()));
        node1.send(simpleMessage);

        Thread.sleep(2000);

        var pathsForNode4 = node1.getShortestPath("node4");
        assertEquals(2, pathsForNode4.size());
        assertTrue(pathsForNode4.stream().anyMatch(path -> "node1".equals(path.getName())));
        assertTrue(pathsForNode4.stream().anyMatch(path -> "node2".equals(path.getName())));

        logger.info("---- Sending another message to verify consistent routing ----");

        node1.send(simpleMessage);
        Thread.sleep(2000);
        assertEquals(3, node1.getAckMessages());

        assertEquals(2, node1.getRouter().getPaths().get("node4").size());
    }
}
