package com.lecturefeed.socket.controller.core;


import com.lecturefeed.authentication.InetAddressSecurityService;
import com.lecturefeed.socket.controller.model.StompPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;


@AllArgsConstructor
@Component
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    private final InetAddressSecurityService inetAddressSecurityService;

    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        if(inetAddressSecurityService.isInetAddressBlocked(request.getRemoteAddress().getAddress())){
            return null;
        }
        // Generate principal with UUID as name
        return new StompPrincipal(UUID.randomUUID().toString());
    }
}
