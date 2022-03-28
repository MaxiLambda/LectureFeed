package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.*;
import com.lecturefeed.model.token.TokenModel;
import com.lecturefeed.session.*;
import com.lecturefeed.socket.controller.service.QuestionService;
import com.lecturefeed.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {

    private final CustomAuthenticationService customAuthenticationService;
    private final SessionManager sessionManager;
    private final QuestionService questionService;

    @GetMapping("/create")
    public Map<String,Object> createNewSession(@RequestHeader("Authorization") String stringToken) {
        TokenModel token = new TokenModel(stringToken);
        if (TokenUtils.isValidAdminToken(customAuthenticationService, token)) {
           Session session = sessionManager.createSession();

            Map<String,Object> sessionInformation = new HashMap<>();
            sessionInformation.put("id",session.getId());
            sessionInformation.put("sessionCode",session.getSessionCode());
            return sessionInformation;
        }
        throw new BadCredentialsException(String.format("No admin rights for token %s", token.getToken()));
    }

    @GetMapping("/{sessionId}/initial")
    public Map<String,Object> getSessionData(@PathVariable("sessionId") Integer sessionId, @RequestHeader("Authorization") String stringToken) {
        TokenModel token = new TokenModel(stringToken);
        if (!(TokenUtils.isValidAdminToken(customAuthenticationService,token) || sessionManager.getSession(TokenUtils.getTokenValue(customAuthenticationService, "sessionId", token).asInt()).isPresent())) throw new BadCredentialsException(String.format("No valid token %s in request body", token.getToken()));
        Session session = sessionManager.getSession(sessionId).orElseThrow(NoSessionFoundException::new);
        ArrayList<Participant> participants = session.getParticipants();
        ArrayList<QuestionModel> questions = session.getQuestions();

        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("questions", questions);
        sessionData.put("participants", participants);
        return sessionData;
    }

    @PostMapping("/{sessionId}/question/create")
    public QuestionModel createQuestion(@RequestBody QuestionModel questionModel, @PathVariable("sessionId") Integer sessionId){

        sessionManager.getSession(sessionId).orElseThrow(NoSessionFoundException::new).addQuestion(questionModel);

        //sendToAll
        questionService.sendQuestion(questionModel,sessionId);

        return questionModel;

    }
}
