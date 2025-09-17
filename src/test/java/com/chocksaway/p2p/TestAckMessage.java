package com.chocksaway.p2p;

import com.chocksaway.p2p.message.AckMessage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestAckMessage {
    @Test
    public void testAckMessage() {
        var node1 = new Node("node1", 8011);
        var node2 = new Node("node2", 8012);
        var ackMessage = new AckMessage(node1.getBaseNode(), "ack message", null, node2.getBaseNode());
        ackMessage.buildLink(node1.getBaseNode(), node2.getBaseNode());
        var link = ackMessage.getLink();
        assertNotNull(link);
        assertEquals("node2", link.from().getName());
        assertEquals("node1", link.to().getName());
    }
}
