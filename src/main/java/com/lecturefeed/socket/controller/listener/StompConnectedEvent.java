package com.lecturefeed.socket.controller.listener;

import com.lecturefeed.socket.controller.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@AllArgsConstructor
@Component
public class StompConnectedEvent implements ApplicationListener<SessionConnectedEvent> {

    @Getter
    private final AdminService adminService;


    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        adminService.addPrincipal(event.getUser());
    }
}