package com.chocksaway.p2p.app.service;

import com.chocksaway.p2p.Node;
import com.chocksaway.p2p.Peer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class MessageService {
    public Flux<String> receive() {
        var peer2 = new Peer("peer2");
        var node2 = new Node("node2", 8002);

        peer2.start(node2);

        return node2.getMessageFlux();
    }
}
