package com.lecturefeed.utils;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import lombok.Getter;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Map;

public class TokenUtils {

    @Getter
    private static CustomAuthenticationService customAuthenticationService;

    public static UserRole getUserRole(TokenModel token){
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

    public static int getSessionId(TokenModel token){
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
