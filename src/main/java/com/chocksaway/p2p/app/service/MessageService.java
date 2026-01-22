package com.chocksaway.p2p.app.service;

import com.chocksaway.p2p.message.log.LogMessage;
import reactor.core.publisher.Flux;

public interface MessageService {
    Flux<String> receive();
    void publish(LogMessage logMessage);
    String clear();
}
