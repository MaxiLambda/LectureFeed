package com.lecturefeed.socket.controller;

import com.lecturefeed.manager.SessionManager;
import com.lecturefeed.socket.controller.service.SessionDataService;
import com.lecturefeed.utils.PrincipalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import java.security.Principal;


@RequiredArgsConstructor
@Controller
public class SessionController {
    private final SessionManager sessionManager;
    private final SessionDataService sessionDataService;

    @MessageMapping("/participant/session/{sessionId}/mood/{participantId}/{rating}")
    public void ratingChange(@DestinationVariable Integer sessionId, @DestinationVariable Integer participantId, @DestinationVariable Integer rating, Principal principal){
        int userId = PrincipalUtils.getClaim("id",principal).asInt();
    }

}
