package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
public class AuthenticationController {

    @Getter
    private final CustomAuthenticationService customAuthenticationService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/auth/admin")
    public TokenModel adminAuth(@RequestParam String sessionCode, @RequestParam Integer sessionId, @RequestParam String username) {
        //TODO: validate session code for given session
        //TODO: get right sessionId from Session-controller
        Integer userId = 1;
        return createToken(userId,username, UserRole.ADMINISTRATOR, sessionId);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/auth/participant")
    public TokenModel participantAuth(@RequestParam String sessionCode, @RequestParam Integer sessionId, @RequestParam String username) {
        //TODO: validate session code for given session
        //TODO: get right sessionId from Session-controller
        Integer userId = 99;
        return createToken(userId,username, UserRole.PARTICIPANT, sessionId);
    }

}
