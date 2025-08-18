package com.chocksaway.p2p.app.service;

import com.chocksaway.p2p.Node;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class MessageService {
    public Flux<String> receive() {
        var node2 = new Node("node2", 8002);

        node2.start();

        return node2.getMessageFlux();
    }
}
