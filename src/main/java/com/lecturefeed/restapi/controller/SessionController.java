package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.*;
import com.lecturefeed.session.*;
import com.lecturefeed.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Service
@RequiredArgsConstructor
public class SessionController {

    private final CustomAuthenticationService customAuthenticationService;
    private final SessionManager sessionManager;

    @PostMapping("/session/create")
    public Map<String,Object> createNewSession(@RequestBody TokenModel token) {

        if (TokenUtils.isValidAdminToken(customAuthenticationService, token)) {
           Session session = sessionManager.createSession();

            Map<String,Object> sessionInformation = new HashMap<>();
            sessionInformation.put("id",session.getId());
            sessionInformation.put("sessionCode",session.getSessionCode());
            return sessionInformation;
        }
        throw new BadCredentialsException(String.format("No admin rights for token %s", token.getToken()));
    }
}
