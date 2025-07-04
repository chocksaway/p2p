package com.chocksaway.p2p;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestNode {
    @Test
    public void testNodeHasAttributes() throws NoSuchFieldException {
        var nameField = Node.class.getDeclaredField("name");
        assertNotNull(nameField);
    }
}
