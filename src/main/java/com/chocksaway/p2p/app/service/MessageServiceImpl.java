package com.chocksaway.p2p.app.service;

import com.chocksaway.p2p.message.NodeLogMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MessageServiceImpl implements MessageService {
    // replay the last 100 messages to new subscribers
    private final AtomicReference<Sinks.Many<String>> sinkRef =
            new AtomicReference<>(Sinks.many().replay().limit(100));

    @Override
    public Flux<String> receive() {
        return sinkRef.get().asFlux();
    }

    @Override
    public void publish(NodeLogMessage nodeLogMessage) {
        String formatted = String.format("%s [%s]: %s", Instant.now().toString(),
                nodeLogMessage.nodeId(), nodeLogMessage.message());
        sinkRef.get().tryEmitNext(formatted);
    }

    @Override
    public String clear() {
        // Atomically replace the sink with a fresh replay sink to drop the cached messages.
        sinkRef.set(Sinks.many().replay().limit(100));
        return "OK";
    }
}