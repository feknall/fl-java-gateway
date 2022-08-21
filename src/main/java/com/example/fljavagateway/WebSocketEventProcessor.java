package com.example.fljavagateway;

import com.google.protobuf.ByteString;
import org.hyperledger.fabric.client.ChaincodeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
public class WebSocketEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(EventListener.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String baseUrl = "http://localhost:9090/events/";

    private WebSocketSession session;

    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    private byte[] toByteArray(ChaincodeEvent event) {
        return Info.Event.newBuilder()
                .setName(event.getEventName())
                .setPayload(ByteString.copyFrom(event.getPayload()))
                .build()
                .toByteArray();
    }

    public void sendEvent(ChaincodeEvent event) {
        try {
            session.sendMessage(new BinaryMessage(toByteArray(event)));
        } catch (IOException e) {
            logger.debug("sendEvent failed", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.debug("sendEvent failed", e);
        }
    }

}
