package com.lecturefeed.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
public class PreAuthTokenHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {

    @Getter
    private final AuthenticatorService authenticatorService;

    private final String authHeaderName = "Authorization";

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return authenticatorService.getAuthenticated(request.getHeader(authHeaderName));
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }

}
