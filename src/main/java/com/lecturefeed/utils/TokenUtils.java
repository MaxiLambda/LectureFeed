package com.lecturefeed.utils;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import com.lecturefeed.session.Session;
import com.lecturefeed.session.SessionManager;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private final static Calendar calendar = Calendar.getInstance();
    private final static int DAYS_TILL_EXPIRATION = 30;

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
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, DAYS_TILL_EXPIRATION);
        Map<String, Object> payloadClaims = new HashMap<>();

        payloadClaims.put("expirationDate",calendar.getTimeInMillis()+"");
        payloadClaims.put("role", UserRole.ADMINISTRATOR.getRole());

        return new TokenModel(customAuthenticationService.generateToken(payloadClaims));
    }

    public static Claim getTokenValue(CustomAuthenticationService customAuthenticationService, String valueName, TokenModel token){
        try {
            DecodedJWT jwt = customAuthenticationService.verifyToken(token.getToken());
            Map<String, Claim> claims = jwt.getClaims();
            return claims.get(valueName);
        }catch (Exception e){
            throw new BadCredentialsException(String.format("token are invalid %s", token));
        }
    }

    public static boolean isValidAdminToken(CustomAuthenticationService customAuthenticationService, TokenModel token){
        boolean isAdmin = false;
        boolean isNotExpired = false;
        try{
            isAdmin = UserRole.ADMINISTRATOR.getRole().equals(getTokenValue(customAuthenticationService,"role",token).asString());
            isNotExpired = System.currentTimeMillis() < getTokenValue(customAuthenticationService,"expirationDate",token).asLong();
        }catch (Exception ignored){

        }
        return isAdmin && isNotExpired;
    }

}
