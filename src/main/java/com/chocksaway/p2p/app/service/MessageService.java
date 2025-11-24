package com.chocksaway.p2p.app.service;

import com.chocksaway.p2p.message.NodeLogMessage;
import reactor.core.publisher.Flux;

public interface MessageService {
    Flux<String> receive();
    void publish(NodeLogMessage nodeLogMessage);
    String clear();
}
