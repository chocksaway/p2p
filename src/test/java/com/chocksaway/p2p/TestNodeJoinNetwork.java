package com.chocksaway.p2p;

import com.chocksaway.p2p.message.RouterMessage;
import com.chocksaway.p2p.message.SimpleMessage;
import com.chocksaway.p2p.route.BaseNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestNodeJoinNetwork {
    @Test
    public void testNodeIsUp() throws InterruptedException {
        var node1 = new Node("node1", 8001);
        var node2 = new Node("node2", 8002);
        var node3 = new Node("node3", 8003);
        node1.start();
        node2.start();
        node3.start();

        var link13= new Link(node1, node3);
        link13.addToFromRouter();

        var message = "Hello, Node 3!";
        var simpleMessage = new SimpleMessage("node3", message);
        simpleMessage.addToPath(new BaseNode(node1.getName(), node1.getName(), node1.getPort()));
        node1.send(simpleMessage);

        assertTrue(node2.connectToNode(node1));

        var routerMessage = new RouterMessage(node2.getBaseNode());

        var link21 = new Link(node2, node1);
        link21.sendMessage(routerMessage);
        Thread.sleep(2000);
        assertEquals(1,node2.getRouter().getPaths().size());
    }
}
