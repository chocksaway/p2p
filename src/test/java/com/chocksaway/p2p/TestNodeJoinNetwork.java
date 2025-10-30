package com.chocksaway.p2p;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestNodeJoinNetwork {
    @Test
    public void testNodeIsUp() {
        var node1 = new Node("node1", 8001);
        var node2 = new Node("node2", 8002);
        node1.start();
        node2.start();

        assertTrue(node1.connectToNode(node2));
    }
}
