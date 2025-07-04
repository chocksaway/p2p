package com.chocksaway.p2p;

import java.util.ArrayList;
import java.util.List;

public final class Node {
    private final String name;
    private final List<String> messsages;

    public Node(String name) {
        this.name = name;
        this.messsages = new ArrayList<>();
    }

    public String name() {
        return name;
    }

    public int getMessageCount() {
        return messsages.size();
    }

    public void removeMessage(String message) {
        messsages.remove(message);
    }

    public void addMessage(String message) {
        messsages.add(message);
    }
}
