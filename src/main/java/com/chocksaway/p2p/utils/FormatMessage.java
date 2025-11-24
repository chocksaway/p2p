package com.chocksaway.p2p.utils;

public class FormatMessage {
    private static final String BRACES = "\\{\\}";

    public String format(String... message) {
        if (message == null || message.length == 0) return null;

        int bracesCount = countWithRegex(message[0]);

        if (bracesCount == 0) return null;
        else if (countWithRegex(message[0]) == message.length - 1) {
            String formatted = message[0];
            for (int i = 1; i < message.length; i++) {
                formatted = formatted.replaceFirst(BRACES, message[i] == null ? "null" : message[i]);
            }
            return formatted;
        }
        return null;
    }

    private int countWithRegex(String s) {
        if (s == null) return 0;
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(BRACES).matcher(s);
        int count = 0;
        while (m.find()) count++;
        return count;
    }
}
