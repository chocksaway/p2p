package com.chocksaway.p2p.app.controller;

import com.chocksaway.p2p.app.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages/receive")
    public Flux<String> receiveMessages() {
        return messageService.receive();
    }
}
