package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import com.lecturefeed.session.Participant;
import com.lecturefeed.session.SessionManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    @Getter
    private SessionManager sessionManager;
    @Getter
    private final CustomAuthenticationService customAuthenticationService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/admin")
    public Object adminAuth(@RequestParam String sessionCode, @RequestParam Integer sessionId, @RequestParam String nickname) {
        if(!sessionManager.isCorrectSessionCode(sessionId,sessionCode)) return null;

        //create token
        TokenModel tokenModel = sessionManager.createToken(customAuthenticationService,nickname, UserRole.ADMINISTRATOR, sessionId);

        //Add new Participant to session
        sessionManager.getSession(sessionId).
                ifPresent(s->s.addParticipant(new Participant(tokenModel.getUserId(),nickname)));
        return tokenModel;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/participant")
    public Object participantAuth(@RequestParam String sessionCode, @RequestParam Integer sessionId, @RequestParam String nickname) {
        if(!sessionManager.isCorrectSessionCode(sessionId,sessionCode)) return null;

        //create token
        TokenModel tokenModel = sessionManager.createToken(customAuthenticationService,nickname, UserRole.PARTICIPANT, sessionId);

        //Add new Participant to session
        sessionManager.getSession(sessionId).
                ifPresent(s->s.addParticipant(new Participant(tokenModel.getUserId(),nickname)));
        return tokenModel;
    }


}
