package com.chocksaway.p2p;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestNode {
    @Test
    public void testNodeHasAttributes() throws NoSuchFieldException {
        var nameField = Node.class.getDeclaredField("name");
        assertNotNull(nameField);
    }

    @Test
    public void testNodeIsRunning() throws InterruptedException {
        var node1 = new Node("node1", 8000);
        node1.start();
        Thread.sleep(2000);
        assertTrue(node1.isRunning());
    }
}
