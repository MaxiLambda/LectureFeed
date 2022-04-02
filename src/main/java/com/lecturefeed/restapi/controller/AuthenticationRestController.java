package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.InetAddressSecurityService;
import com.lecturefeed.authentication.jwt.TokenService;
import com.lecturefeed.manager.ParticipantManager;
import com.lecturefeed.model.ParticipantAuthRequestModel;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.manager.SessionManager;
import com.lecturefeed.utils.HttpServletRequestUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthenticationRestController {

    private final SessionManager sessionManager;
    private final ParticipantManager participantManager;
    private final TokenService tokenService;
    private final InetAddressSecurityService inetAddressSecurityService;

    @GetMapping("/admin")
    public TokenModel adminAuth() {
        return tokenService.createAdminToken();
    }

    @PostMapping("/participant")
    public Object participantAuth(@RequestBody ParticipantAuthRequestModel authRequestModel, HttpServletRequest request) {
        if(!sessionManager.isCorrectSessionCode(authRequestModel.getSessionId(),authRequestModel.getSessionCode()) ||
                inetAddressSecurityService.isInetAddressBlocked(HttpServletRequestUtils.getRemoteAddrByRequest(request)))
            throw new BadCredentialsException("Bad session data");

        sessionManager.checkSessionId(authRequestModel.getSessionId());
        Participant participant = participantManager.createParticipantBySessionId(authRequestModel.getSessionId(), authRequestModel.getNickname());
        //create token
        TokenModel tokenModel = tokenService.createParticipantToken(authRequestModel.getNickname(), UserRole.PARTICIPANT, authRequestModel.getSessionId(), participant.getId());
        int userId = tokenService.getTokenValue("id", tokenModel).asInt();

        return tokenModel;
    }
}
