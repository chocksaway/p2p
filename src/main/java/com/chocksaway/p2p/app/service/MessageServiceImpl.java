package com.chocksaway.p2p.app.service;

import com.chocksaway.p2p.message.NodeLogMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Instant;

@Service
public class MessageServiceImpl implements MessageService {
    // replay the last 100 messages to new subscribers
    private final Sinks.Many<String> sink = Sinks.many().replay().limit(100);

    @Override
    public Flux<String> receive() {
        return sink.asFlux();
    }

    @Override
    public void publish(NodeLogMessage nodeLogMessage) {
        String formatted = String.format("%s [%s]: %s", Instant.now().toString(), nodeLogMessage.nodeId(), nodeLogMessage.message());
        sink.tryEmitNext(formatted);
    }
}