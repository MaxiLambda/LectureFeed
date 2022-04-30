package com.lecturefeed.socket.controller.model;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class WebSocketHolderSession {

    private WebSocketSession webSocketSession = null;
    private Integer participantId = 0;

}
