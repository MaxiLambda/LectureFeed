package com.lecturefeed.utils;

import com.auth0.jwt.interfaces.Claim;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;
import java.util.Map;

public class PrincipalUtils {

    public static Claim getClaim(String value, Principal principal){
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        return ((Map<String, Claim>) token.getCredentials()).get(value);
    }
}
