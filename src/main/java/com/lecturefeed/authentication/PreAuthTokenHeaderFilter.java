package com.lecturefeed.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class PreAuthTokenHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final AuthenticatorService authenticatorService;
    private final InetAddressSecurityService inetAddressSecurityService;
    private final String authHeaderName = "Authorization";

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        if(inetAddressSecurityService.isInetAddressBlocked(getRemoteAddrByRequest(request))){
            return null;
        }
        String authorization = getAuthorizationTokenByRequest(request);
        return authenticatorService.getAuthenticated(authorization);
    }

    private InetAddress getRemoteAddrByRequest(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            try {
                return InetAddress.getByName(request.getRemoteAddr());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getAuthorizationTokenByRequest(HttpServletRequest request){
        String authorization = request.getHeader(authHeaderName);
        if(authorization == null){
            authorization = getAuthorizationTokenByCookieRequest(request);
        }
        return authorization;
    }

    private String getAuthorizationTokenByCookieRequest(HttpServletRequest request){
        List<String> authorizations = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(authHeaderName) && cookie.getValue().length() > 0)
                .map(Cookie::getValue).toList();
        return  authorizations.size() == 1? authorizations.get(0): null;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }

}
