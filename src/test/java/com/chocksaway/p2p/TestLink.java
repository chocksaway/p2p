package com.chocksaway.p2p;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestLink {
    @Test
    public void testLinkOverlay() {
        var node1 = new Node("node1", 8001);
        var node2 = new Node("node2", 8002);
        var node3 = new Node("node3", 8003);
        var node4 = new Node("node4", 8004);

        var link12 = new Link(node1, node2);
        var link13 = new Link(node1, node3);
        var link24 = new Link(node2, node4);
        var link34 = new Link(node3, node4);

        link12.overlay();
        link13.overlay();
        link24.overlay();
        link34.overlay();

        assertEquals(2, node1.getLinks());
        assertEquals(2, node2.getLinks());
        assertEquals(2, node3.getLinks());
        assertEquals(2, node4.getLinks());
    }
}
