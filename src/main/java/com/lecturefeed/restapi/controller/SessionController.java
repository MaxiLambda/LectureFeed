package com.lecturefeed.restapi.controller;

import com.lecturefeed.model.*;
import com.lecturefeed.utils.TokenUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SessionController {

    private SessionManager sessionManager = new SessionManager();

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/session/create")
    public Map<Integer,String> createNewSession(@RequestParam TokenModel token) throws SessionAlreadyExistsException {
        if (UserRole.ADMINISTRATOR.equals(TokenUtils.getUserRole(token))) {

            int sessionId = TokenUtils.getSessionId(token);
            Session session = sessionManager.createNewSession(sessionId);

            Map<Integer,String> sessionInformation = new HashMap<Integer,String>();
            sessionInformation.put(TokenUtils.getSessionId(token), session.getSessionCode());

            return sessionInformation;
        }
        throw new BadCredentialsException(String.format("No admin rights for token %s", token.getToken()));
    }
}
