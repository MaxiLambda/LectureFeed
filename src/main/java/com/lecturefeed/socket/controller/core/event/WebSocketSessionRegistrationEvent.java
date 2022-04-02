package com.lecturefeed.socket.controller.core.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketSessionRegistrationEvent extends ApplicationEvent {
    private WebSocketSession webSocketSession;

    public WebSocketSessionRegistrationEvent(Object source, WebSocketSession webSocketSession) {
        super(source);
        this.webSocketSession = webSocketSession;
    }
    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }
}
