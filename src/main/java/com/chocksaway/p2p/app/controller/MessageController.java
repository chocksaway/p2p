package com.chocksaway.p2p.app.controller;

import com.chocksaway.p2p.app.service.MessageService;
import com.chocksaway.p2p.message.NodeLogMessage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/messages/receive", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> receiveMessages() {
        return messageService.receive();
    }

    @PostMapping(value = "/messages/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendMessage(@RequestBody NodeLogMessage message) {
        messageService.publish(message);
    }
}


