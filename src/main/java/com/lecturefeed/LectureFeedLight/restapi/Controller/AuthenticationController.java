package com.lecturefeed.LectureFeedLight.restapi.Controller;

import com.lecturefeed.LectureFeedLight.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.LectureFeedLight.model.TokenModel;
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
    @RequestMapping(method = RequestMethod.GET, path = "/auth/admin")
    public TokenModel adminAuth() {
        Map<String, Object> payloadClaims = new HashMap<>();
        payloadClaims.put("id", 1);
        payloadClaims.put("username", "Administrator");
        payloadClaims.put("role", "ADMIN");
        return new TokenModel(customAuthenticationService.generateToken(payloadClaims));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method = RequestMethod.GET, path = "/auth/participant")
    public TokenModel participantAuth() {
        Map<String, Object> payloadClaims = new HashMap<>();
        payloadClaims.put("id", 99);
        payloadClaims.put("username", "Participant0815");
        payloadClaims.put("role", "PARTICIPANT");
        return new TokenModel(customAuthenticationService.generateToken(payloadClaims));
    }

}
