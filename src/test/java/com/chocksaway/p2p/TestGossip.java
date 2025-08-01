package com.chocksaway.p2p;

import com.chocksaway.p2p.exception.LinkExistsException;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestGossip {
    @Test
    public void listNodesAttachedToPeer() {
        var node1 = new Node("node1", 8000);
        var node2 = new Node("node2", 8001);
        var link1 = new Link(node1, node2);

        var peer =  new Peer("peer1");
        peer.addLink(link1);
        assertThrows(LinkExistsException.class, () -> peer.addLink(link1));

        assertEquals(1, peer.getRouter().getLinks());
    }

    @Test
    public void testGossipProtocol() {
        var node1 = new Node("node1", 8000);
        var node2 = new Node("node2", 8001);
        var node3 = new Node("node3", 8002);

        var link1 = new Link(node1, node2);
        var link2 = new Link(node1, node3);

        var peer =  new Peer("peer1");

        peer.start(node1);
        peer.start(node2);
        peer.start(node3);

        peer.addLink(link1);
        peer.addLink(link2);


        final String message = "this is a sample message";

        assertTrue(peer.send(link1, message));

        assertEquals(1, node2.getMessageCount());
        assertEquals(1, node3.getMessageCount());
    }




}