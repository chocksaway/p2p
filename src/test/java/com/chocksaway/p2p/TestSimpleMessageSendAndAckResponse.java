package com.chocksaway.p2p;

import com.chocksaway.p2p.message.SimpleMessage;
import com.chocksaway.p2p.route.BaseNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestSimpleMessageSendAndAckResponse {
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
        var simpleMessage = new SimpleMessage("node4", message);
        simpleMessage.addToPath(new BaseNode(node1.getName(), node1.getName(), node1.getPort()));
        node1.send(simpleMessage);

        Thread.sleep(2000);

        assertEquals(2, node4.getMessages());
        assertEquals(2, node1.getAckMessages());
    }
}
