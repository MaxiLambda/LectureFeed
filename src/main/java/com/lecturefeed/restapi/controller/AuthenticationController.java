package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.el.parser.Token;
import org.springframework.web.bind.annotation.*;

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

    private TokenModel createToken(Integer id, String username, UserRole role, Integer sessionId){
        Map<String, Object> payloadClaims = new HashMap<>();
        payloadClaims.put("id",id);
        payloadClaims.put("username", username);
        payloadClaims.put("role", role.getRole());
        payloadClaims.put("sessionId", sessionId);
        return new TokenModel(customAuthenticationService.generateToken(payloadClaims));
    }
}
