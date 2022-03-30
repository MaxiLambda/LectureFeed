package com.lecturefeed.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SecurityContextHolderUtils {

    public static boolean isCurrentUserAdmin(){
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> collection = authentication.getAuthorities().stream()
                .filter(grantedAuthority -> Objects.equals(grantedAuthority.getAuthority(), "ROLE_ADMIN")).collect(Collectors.toList());
        return collection.size() > 0;
    }
}
