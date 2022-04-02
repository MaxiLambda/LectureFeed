package com.lecturefeed.socket.controller.core.event;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@AllArgsConstructor
@Component
public class WebSocketSessionEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void onWebSocketSessionCreated(final WebSocketSession webSocketSession) {
        WebSocketSessionRegistrationEvent event = new WebSocketSessionRegistrationEvent(this, webSocketSession);
        applicationEventPublisher.publishEvent(event);
    }


}
