package com.lecturefeed.authentication;

import com.lecturefeed.utils.HttpServletRequestUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class PreAuthTokenHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final AuthenticatorService authenticatorService;
    private final InetAddressSecurityService inetAddressSecurityService;
    private final String authHeaderName = "Authorization";

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        if(inetAddressSecurityService.isInetAddressBlocked(HttpServletRequestUtils.getRemoteAddrByRequest(request))){
            return null;
        }
        String authorization = getAuthorizationTokenByRequest(request);
        return authenticatorService.getAuthenticated(authorization);
    }



    private String getAuthorizationTokenByRequest(HttpServletRequest request){
        String authorization = request.getHeader(authHeaderName);
        if(authorization == null){
            authorization = getAuthorizationTokenByCookieRequest(request);
        }
        return authorization;
    }

    private String getAuthorizationTokenByCookieRequest(HttpServletRequest request){
        if(request.getCookies() == null) return null;
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
