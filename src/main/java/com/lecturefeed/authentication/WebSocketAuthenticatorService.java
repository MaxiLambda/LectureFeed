package com.lecturefeed.authentication;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
@Component
public class WebSocketAuthenticatorService {

    @Getter
    private final CustomAuthenticationService customAuthenticationService;

    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String  token) throws AuthenticationException {
        if (token == null || token.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("token was null or empty.");
        }
        String username;
        String role;
        DecodedJWT jwt;
        try {
            jwt = customAuthenticationService.verifyToken(token);
            Map<String, Claim> claims = jwt.getClaims();
            username = claims.get("username").asString();
            role = claims.get("role").asString();
        }catch (Exception e){
            throw new BadCredentialsException(String.format("token are invalid %s", token));
        }

        return new UsernamePasswordAuthenticationToken(
                username,
                jwt.getClaims(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_"+role.toUpperCase())) // MUST provide at least one role
        );
    }
}