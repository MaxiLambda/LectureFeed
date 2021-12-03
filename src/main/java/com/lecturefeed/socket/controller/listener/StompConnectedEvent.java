package com.lecturefeed.socket.controller.listener;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@AllArgsConstructor
@Component
public class StompConnectedEvent implements ApplicationListener<SessionConnectedEvent> {

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
//        adminService.addPrincipal(event.getUser());
    }
}