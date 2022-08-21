package com.example.fljavagateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.List;

public class SocketHandler extends AbstractWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(SocketHandler.class);
    private final WebSocketEventProcessor eventProcessor;

    public SocketHandler(WebSocketEventProcessor eventProcessor) {
        logger.debug("Hi!");
        this.eventProcessor = eventProcessor;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
//        try {
//            session.sendMessage(new TextMessage("hello"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        logger.info("New socket connected successfully.");
        eventProcessor.setSession(session);
    }
}
