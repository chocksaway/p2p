package com.chocksaway.p2p.message;

import com.chocksaway.p2p.utils.FormatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class SendMessage {
    private static final Logger logger = LogManager.getLogger(SendMessage.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WebClient webClient = WebClient.create("http://127.0.0.1:8080");

    public void logAndSend(String nodeId, String... message) {
        var fmt = new FormatMessage();
        var formattedMessage = fmt.format(message);
        logger.info("Sending message to node {}: {}", nodeId, formattedMessage);
        send(nodeId, formattedMessage);
    }

    private void send(String nodeId, String message) {
        String jsonPayload = "";
        NodeLogMessage nodeLogMessage = new NodeLogMessage(nodeId, message);
        try {
            jsonPayload = objectMapper.writeValueAsString(nodeLogMessage);
        } catch (JsonProcessingException ex) {
            logger.error(ex.getMessage());
        }

        try {
            webClient.post()
                    .uri("/messages/send")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(jsonPayload)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .subscribe(
                            unused -> logger.info("Message sent successfully"),
                            error -> logger.error("Error sending message: {}", error.getMessage())
                    );
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

    }
}
