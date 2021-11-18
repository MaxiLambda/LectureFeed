package com.lecturefeed.utils;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import com.lecturefeed.session.Session;
import com.lecturefeed.session.SessionManager;
import org.springframework.security.authentication.BadCredentialsException;
import java.util.HashMap;
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

    public static TokenModel createAdminToken(CustomAuthenticationService customAuthenticationService) {
        Map<String, Object> payloadClaims = new HashMap<>();

        payloadClaims.put("role", UserRole.ADMINISTRATOR.getRole());
        return new TokenModel(customAuthenticationService.generateToken(payloadClaims));
    }

    public static String getTokenValue(CustomAuthenticationService customAuthenticationService, String valueName, TokenModel token){
        try {
            DecodedJWT jwt = customAuthenticationService.verifyToken(token.getToken());
            Map<String, Claim> claims = jwt.getClaims();
            return claims.get(valueName).asString();
        }catch (Exception e){
            throw new BadCredentialsException(String.format("token are invalid %s", token));
        }
    }

    public static boolean isValidAdminToken(CustomAuthenticationService customAuthenticationService, TokenModel token){
        return UserRole.ADMINISTRATOR.getRole().equals(TokenUtils.getTokenValue(customAuthenticationService,"role",token));
    }

}
