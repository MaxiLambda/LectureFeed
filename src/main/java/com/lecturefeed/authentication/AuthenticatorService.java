package com.lecturefeed.authentication;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import com.lecturefeed.session.Session;
import com.lecturefeed.session.SessionManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@AllArgsConstructor
@Component
public class AuthenticatorService {

    private final CustomAuthenticationService customAuthenticationService;

    public UsernamePasswordAuthenticationToken getAuthenticated(final String  token) throws AuthenticationException {
        if (token == null || token.trim().isEmpty()) {
            return null;
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