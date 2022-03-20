package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.ParticipantAuthRequestModel;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import com.lecturefeed.session.Participant;
import com.lecturefeed.session.SessionManager;
import com.lecturefeed.utils.TokenUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationRestController {

    @Getter
    private final SessionManager sessionManager;

    @Getter
    private final CustomAuthenticationService customAuthenticationService;

    @GetMapping("/admin")
    public TokenModel adminAuth() {
        //create and return token
        return TokenUtils.createAdminToken(customAuthenticationService);

    }

    @PostMapping("/participant")
    public Object participantAuth(@RequestBody ParticipantAuthRequestModel authRequestModel) {
        if(!sessionManager.isCorrectSessionCode(authRequestModel.getSessionId(),authRequestModel.getSessionCode()))
            throw new BadCredentialsException("Bad session data");

        //create token
        TokenModel tokenModel = TokenUtils.createParticipantToken(customAuthenticationService,sessionManager,authRequestModel.getNickname(), UserRole.PARTICIPANT, authRequestModel.getSessionId());
        int userId = TokenUtils.getTokenValue(customAuthenticationService,"id",tokenModel).asInt();
        //Add new Participant to session
        sessionManager.getSession(authRequestModel.getSessionId()).
                ifPresent(s->s.addParticipant(new Participant(userId, authRequestModel.getNickname())));
        return tokenModel;
    }
}
