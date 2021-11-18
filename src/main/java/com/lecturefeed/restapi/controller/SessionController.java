package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.*;
import com.lecturefeed.session.*;
import com.lecturefeed.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@Service
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {

    private final CustomAuthenticationService customAuthenticationService;
    private final SessionManager sessionManager;

    @PostMapping(path = "/create")
    public Map<String,Object> createNewSession(@RequestParam TokenModel token) {

        if (TokenUtils.isValidAdminToken(customAuthenticationService, token)) {
           Session session = sessionManager.createSession();

            Map<String,Object> sessionInformation = new HashMap<>();
            sessionInformation.put("id",session.getId());
            sessionInformation.put("sessionCode",session.getSessionCode());
            return sessionInformation;
        }
        throw new BadCredentialsException(String.format("No admin rights for token %s", token.getToken()));
    }

    @PostMapping(path = "/{sessionId}/initial")
    public Map<String,Object> getSessionData(@RequestParam TokenModel token) {
        int sessionId = Integer.parseInt(TokenUtils.getTokenValue(customAuthenticationService,"sessionId",token));
        Session session = sessionManager.getSession(sessionId).get();
        if(session != null)
        {
            ArrayList<Participant> participants = session.getParticipants();
            ArrayList<Question> questions = session.getQuestions();

            Map<String, Object> sessionData = new HashMap<>();
            sessionData.put("questions", questions);
            sessionData.put("participants", participants);
        }
        throw new BadCredentialsException(String.format("Invalid sessionId %s", sessionId));
    }
}
