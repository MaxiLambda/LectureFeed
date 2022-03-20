package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.*;
import com.lecturefeed.session.*;
import com.lecturefeed.socket.controller.service.QuestionService;
import com.lecturefeed.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionRestController {

    private final CustomAuthenticationService customAuthenticationService;
    private final SessionManager sessionManager;
    private final QuestionService questionService;

    @PostMapping("/presenter/create")
    public Map<String,Object> createNewSession(@RequestHeader("Authorization") String stringToken, @RequestBody CreateSessionModel createSessionModel) {
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

    @GetMapping("/presenter/{sessionId}/initial")
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

    @DeleteMapping("/presenter/{sessionId}")
    public void deleteSession(@PathVariable("sessionId") Integer sessionId, @RequestHeader("Authorization") String stringToken) {
        TokenModel token = new TokenModel(stringToken);
        if (!(TokenUtils.isValidAdminToken(customAuthenticationService,token) || sessionManager.getSession(TokenUtils.getTokenValue(customAuthenticationService, "sessionId", token).asInt()).isPresent())) throw new BadCredentialsException(String.format("No valid token %s in request body", token.getToken()));
        //Session session = sessionManager.getSession(sessionId).orElseThrow(NoSessionFoundException::new);

    }

    @PostMapping("/{sessionId}/question/create")
    public QuestionModel createQuestion(@RequestBody QuestionModel questionModel, @PathVariable("sessionId") Integer sessionId){

        sessionManager.getSession(sessionId).orElseThrow(NoSessionFoundException::new).addQuestion(questionModel);

        //sendToAll
        questionService.sendQuestion(questionModel,sessionId);

        return questionModel;

    }

    //TODO: LFD-79 //Aktuell Testdaten
    @GetMapping("/presenter/sessions/metadata")
    public List<SessionMetadata> getSessionsMetadata(@RequestHeader("Authorization") String stringToken) {
        TokenModel token = new TokenModel(stringToken);
        if (!(TokenUtils.isValidAdminToken(customAuthenticationService,token) || sessionManager.getSession(TokenUtils.getTokenValue(customAuthenticationService, "sessionId", token).asInt()).isPresent())) throw new BadCredentialsException(String.format("No valid token %s in request body", token.getToken()));

        List<SessionMetadata> dummyValues = new ArrayList<>();
        SessionMetadata dummyValue1 = new SessionMetadata();
        dummyValue1.setSessionId(99);
        dummyValue1.setName("DummyName1");
        dummyValue1.setClosed(new Date().getTime());
        dummyValues.add(dummyValue1);
        SessionMetadata dummyValue2 = new SessionMetadata();
        dummyValue2.setSessionId(98);
        dummyValue2.setName("DummyName2");
        dummyValue2.setClosed(new Date().getTime());
        dummyValues.add(dummyValue2);
        return dummyValues;
    }

}
