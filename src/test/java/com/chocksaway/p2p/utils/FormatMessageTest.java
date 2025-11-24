package com.chocksaway.p2p.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FormatMessageTest {
    @Test
    public void testFormatMessage() {
        var formatMessage = new FormatMessage();
        String[] input = {"Values are {} {} {}", "item1", "item2", "item3"};

        assertEquals("Values are item1 item2 item3", formatMessage.format(input));
    }

    @Test
    public void testFormatWithInvalidMessage() {
        var formatMessage = new FormatMessage();
        String[] input = {"Values are {} {}", "item1", "item2", "item3"};
        assertNull(formatMessage.format(input));
    }

    @Test
    public void testFormatWithEmptyMessage() {
        var formatMessage = new FormatMessage();
        String[] input = {""};
        assertNull(formatMessage.format(input));
    }


}
