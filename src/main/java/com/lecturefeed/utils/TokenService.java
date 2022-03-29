package com.lecturefeed.utils;

import com.auth0.jwt.interfaces.Claim;
import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import com.lecturefeed.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenService {
    private final CustomAuthenticationService authenticationService;

    public boolean isValidAdminToken(TokenModel token){
        return TokenUtils.isValidAdminToken(authenticationService,token);
    }

    public Claim getTokenValue(String valueName, TokenModel token){
        return TokenUtils.getTokenValue(authenticationService,valueName,token);
    }

    public TokenModel createParticipantToken(SessionManager sessionManager, String nickname, UserRole role, Integer sessionId){
        return TokenUtils.createParticipantToken(authenticationService,sessionManager,nickname,role,sessionId);
    }

    public TokenModel createAdminToken(){
        return TokenUtils.createAdminToken(authenticationService);
    }
}
