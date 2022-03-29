package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.TokenService;
import com.lecturefeed.model.ParticipantAuthRequestModel;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import com.lecturefeed.session.Participant;
import com.lecturefeed.session.SessionManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthenticationRestController {

    @Getter
    private final SessionManager sessionManager;
    private final TokenService tokenService;

    @GetMapping("/admin")
    public TokenModel adminAuth() {
        //create and return token
        return tokenService.createAdminToken();

    }

    @PostMapping("/participant")
    public Object participantAuth(@RequestBody ParticipantAuthRequestModel authRequestModel) {
        if(!sessionManager.isCorrectSessionCode(authRequestModel.getSessionId(),authRequestModel.getSessionCode()))
            throw new BadCredentialsException("Bad session data");

        //create token
        TokenModel tokenModel = tokenService.createParticipantToken(authRequestModel.getNickname(), UserRole.PARTICIPANT, authRequestModel.getSessionId());
        int userId = tokenService.getTokenValue("id", tokenModel).asInt();
        //Add new Participant to session
        sessionManager.getSessionById(authRequestModel.getSessionId()).
                ifPresent(s->s.addParticipant(new Participant(userId, authRequestModel.getNickname())));
        return tokenModel;
    }
}
