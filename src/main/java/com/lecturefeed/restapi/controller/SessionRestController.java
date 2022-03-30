package com.lecturefeed.restapi.controller;

import com.lecturefeed.model.*;
import com.lecturefeed.session.*;
import com.lecturefeed.socket.controller.service.QuestionService;

import com.lecturefeed.utils.TokenService;
import com.lecturefeed.socket.controller.service.SessionDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionRestController {

    private final TokenService validator;
    private final SessionManager sessionManager;
    private final QuestionService questionService;
    private final SessionDataService sessionDataService;

    @PostMapping("/presenter/create")
    public Map<String,Object> createNewSession(@RequestHeader("Authorization") String stringToken, @RequestBody CreateSessionModel createSessionModel) {
        TokenModel token = new TokenModel(stringToken);
        if (validator.isValidAdminToken(token)) {
           Session session = sessionManager.createSession();

            Map<String,Object> sessionInformation = new HashMap<>();
            sessionInformation.put("id",session.getId());
            sessionInformation.put("sessionCode",session.getSessionCode());
            return sessionInformation;
        }
        throw new BadCredentialsException(String.format("No admin rights for token %s", token.getToken()));
    }

    @GetMapping("/presenter/{sessionId}/close")
    public void closeSession(@RequestHeader("Authorization") String stringToken, @PathVariable("sessionId") Integer sessionId) {
        TokenModel token = new TokenModel(stringToken);
        if (validator.isValidAdminToken(customAuthenticationService, token)) {
            Optional<Session> session = sessionManager.getSessionById(sessionId);
            if(session.isPresent()){
                session.get().setClosed(new Date().getTime());
                sessionDataService.sendClose(sessionId);
            }else
                throw new BadCredentialsException(String.format("No Session with id %d are found", sessionId));
        }else
            throw new BadCredentialsException(String.format("No admin rights for token %s", token.getToken()));

    }

    @GetMapping("/presenter/{sessionId}/initial")
    public Map<String,Object> getSessionData(@PathVariable("sessionId") Integer sessionId, @RequestHeader("Authorization") String stringToken) {
        TokenModel token = new TokenModel(stringToken);

        if (!(validator.isValidAdminToken(token) || sessionManager.getSession(validator.getTokenValue("sessionId", token).asInt()).isPresent())) throw new BadCredentialsException(String.format("No valid token %s in request body", token.getToken()));
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

        if (!(validator.isValidAdminToken(token) || sessionManager.getSession(validator.getTokenValue("sessionId", token).asInt()).isPresent())) throw new BadCredentialsException(String.format("No valid token %s in request body", token.getToken()));

        //Session session = sessionManager.getSession(sessionId).orElseThrow(NoSessionFoundException::new);

    }

    @PostMapping("/{sessionId}/question/create")
    public QuestionModel createQuestion(@RequestBody QuestionModel questionModel, @PathVariable("sessionId") Integer sessionId){

        sessionManager.getSessionById(sessionId).orElseThrow(NoSessionFoundException::new).addQuestion(questionModel);

        //sendToAll
        questionService.sendQuestion(questionModel,sessionId);

        return questionModel;

    }

    //TODO: LFD-79 //Aktuell Testdaten
    @GetMapping("/presenter/sessions/metadata")
    public List<SessionMetadata> getSessionsMetadata(@RequestHeader("Authorization") String stringToken) {
        TokenModel token = new TokenModel(stringToken);
        if (!(validator.isValidAdminToken(token) || sessionManager.getSession(validator.getTokenValue("sessionId", token).asInt()).isPresent())) throw new BadCredentialsException(String.format("No valid token %s in request body", token.getToken()));


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
