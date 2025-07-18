package com.chocksaway.p2p;

import com.chocksaway.p2p.route.Router;

import java.util.ArrayList;
import java.util.List;

public class Peer {
    private final String name;
    private final List<Node> nodes;
    private final Router router;

    public Peer(String name) {
        this.name = name;
        this.router = new Router();
        this.nodes = new ArrayList<>();
    }

    public void addLink(Link link) {
        this.router.addLink(link);
    }

    public Router getRouter() {
        return router;
    }

    public void start(Node node) {
        node.setName(this.name + "::" + node.getName());
        node.start();
        nodes.add(node);
    }

    public boolean send(Link link, String message) {
        return this.router.send(link, message);
    }

    public int findNumberOfMessages(String nodeName) {
        var node = this.nodes.stream().filter(each -> each.getName().equals(nodeName)).findFirst();
        return node.map(Node::getMessageCount).orElse(0);
    }
}
