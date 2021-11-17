package com.lecturefeed.utils;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import com.lecturefeed.session.Session;
import com.lecturefeed.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import lombok.Getter;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Map;

public class TokenUtils {

    public static TokenModel createParticipantToken(CustomAuthenticationService customAuthenticationService, SessionManager sessionManager, String nickname, UserRole role, Integer sessionId){
        Map<String, Object> payloadClaims = new HashMap<>();
        int id = sessionManager.getSession(sessionId).
                map(Session::getNextUserId).
                orElseThrow();
        payloadClaims.put("id",id);
        payloadClaims.put("username", nickname);
        payloadClaims.put("role", role.getRole());
        payloadClaims.put("sessionId", sessionId);
        return new TokenModel(customAuthenticationService.generateToken(payloadClaims));
    }

    public static TokenModel createAdminToken(CustomAuthenticationService customAuthenticationService){
        Map<String, Object> payloadClaims = new HashMap<>();

        payloadClaims.put("role", UserRole.ADMINISTRATOR.getRole());
        return new TokenModel(customAuthenticationService.generateToken(payloadClaims));

    public static UserRole getUserRole(CustomAuthenticationService customAuthenticationService,TokenModel token){
        try {
            DecodedJWT jwt = customAuthenticationService.verifyToken(token.getToken());
            Map<String, Claim> claims = jwt.getClaims();
            String roleAsString = claims.get("role").asString();
            UserRole role = UserRole.fromString(roleAsString);
            return role;
        }catch (Exception e){
            throw new BadCredentialsException(String.format("token are invalid %s", token));
        }
    }

    public static int getSessionId(CustomAuthenticationService customAuthenticationService, TokenModel token){
        try {
            DecodedJWT jwt = customAuthenticationService.verifyToken(token.getToken());
            Map<String, Claim> claims = jwt.getClaims();
            int sessionId = claims.get("sessionId").asInt();
            return sessionId;
        }catch (Exception e){
            throw new BadCredentialsException(String.format("token are invalid %s", token));
        }
    }
}
