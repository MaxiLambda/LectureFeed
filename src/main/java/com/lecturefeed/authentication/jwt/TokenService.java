package com.lecturefeed.authentication.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Component
public class TokenService {

    private final CustomAuthenticationService customAuthenticationService;

    private final Calendar calendar = Calendar.getInstance();
    private final int DAYS_TILL_EXPIRATION = 30;

    public void checkSessionIdByToken(String token, int sessionId){
        if(getTokenValue("sessionId", token).asInt() != sessionId){
            throw new BadCredentialsException("Permission denied");
        }
    }

    public TokenModel createParticipantToken(String nickname, UserRole role, Integer sessionId, Integer participantId){
        Map<String, Object> payloadClaims = new HashMap<>();
        payloadClaims.put("id", participantId);
        payloadClaims.put("username", nickname);
        payloadClaims.put("role", role.getRole());
        payloadClaims.put("sessionId", sessionId);
        return new TokenModel(customAuthenticationService.generateToken(payloadClaims));
    }

    public TokenModel createAdminToken() {
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, DAYS_TILL_EXPIRATION);
        Map<String, Object> payloadClaims = new HashMap<>();

        payloadClaims.put("expirationDate",calendar.getTimeInMillis());
        payloadClaims.put("role", UserRole.ADMINISTRATOR.getRole());
        payloadClaims.put("username", UserRole.ADMINISTRATOR.getRole());

        return new TokenModel(customAuthenticationService.generateToken(payloadClaims));
    }

    public Claim getTokenValue(String valueName, TokenModel token){
        return getTokenValue(valueName, token.getToken());
    }

    public Claim getTokenValue(String valueName, String token){
        Map<String, Claim> claims = getTokenClaims(token);
        return claims.get(valueName);
    }

    public Map<String, Claim> getTokenClaims(String token){
        try {
            DecodedJWT jwt = customAuthenticationService.verifyToken(token);
            return jwt.getClaims();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("token are invalid %s", token));
        }
    }

    public boolean isValidAdminToken(String token){
        boolean isAdmin = false;
        boolean isNotExpired = false;
        try{
            isAdmin = UserRole.ADMINISTRATOR.getRole().equals(getTokenValue("role", token).asString());
            isNotExpired = System.currentTimeMillis() < getTokenValue("expirationDate", token).asLong();
        }catch (Exception ignored){

        }
        return isAdmin && isNotExpired;
    }

    public Map<String, Claim> getClaimsByValidToken(String token){
        try{
            Map<String, Claim> claims = getTokenClaims(token);
            if(claims.containsKey("expirationDate") && System.currentTimeMillis() < claims.get("expirationDate").asLong()){
                return claims;
            }
        }catch (Exception ignored){
            return null;
        }
        return null;
    }

}
