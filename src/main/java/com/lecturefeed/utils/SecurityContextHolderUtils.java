package com.lecturefeed.utils;

import com.auth0.jwt.interfaces.Claim;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SecurityContextHolderUtils {

    public static boolean isCurrentUserAdmin(){
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> collection = authentication.getAuthorities().stream()
                .filter(grantedAuthority -> Objects.equals(grantedAuthority.getAuthority(), "ROLE_ADMIN")).toList();
        return collection.size() > 0;
    }

    public static UsernamePasswordAuthenticationToken getCurrentPrincipal(){
        return (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

    public static Map<String, Claim> getCurrentAuthenticationClaims(){
        return (Map<String, Claim>) getCurrentPrincipal().getCredentials();
    }

    public static Optional<Integer> getCurrentAuthenticationId(){
        return Optional.ofNullable(getCurrentAuthenticationClaims().get("id")).map(Claim::asInt);
    }

}
