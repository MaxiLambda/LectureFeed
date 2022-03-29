package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.TokenService;
import com.lecturefeed.model.*;
import com.lecturefeed.session.*;
import com.lecturefeed.socket.controller.service.QuestionService;
import com.lecturefeed.socket.controller.service.SessionDataService;
import com.lecturefeed.utils.SecurityContextHolderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/session")
public class SessionRestController {

    private final SessionManager sessionManager;
    private final QuestionService questionService;
    private final SessionDataService sessionDataService;
    private final TokenService tokenService;

    @PostMapping("/presenter/create")
    public Map<String,Object> createNewSession(@RequestBody CreateSessionModel createSessionModel) {
        Session session = sessionManager.createSession();

        Map<String,Object> sessionInformation = new HashMap<>();
        sessionInformation.put("id",session.getId());
        sessionInformation.put("sessionCode",session.getSessionCode());
        return sessionInformation;
    }

    @GetMapping("/presenter/{sessionId}/close")
    public void closeSession(@PathVariable("sessionId") Integer sessionId) {
        Optional<Session> session = sessionManager.getSessionById(sessionId);
        if(session.isPresent()){
            session.get().setClosed(new Date().getTime());
            sessionDataService.sendClose(sessionId);
        }else
            throw new BadCredentialsException(String.format("No Session with id %d are found", sessionId));

    }

    @GetMapping("/{sessionId}/initial")
    public Map<String,Object> getSessionData(@PathVariable("sessionId") Integer sessionId, @RequestHeader("Authorization") String token) {
        if(!SecurityContextHolderUtils.isCurrentUserAdmin()) tokenService.checkSessionIdByToken(token, sessionId);
        sessionManager.checkSessionId(sessionId);

        Session session = sessionManager.getSessionById(sessionId).orElseThrow(NoSessionFoundException::new);
        ArrayList<Participant> participants = session.getParticipants();
        ArrayList<QuestionModel> questions = session.getQuestions();

        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("questions", questions);
        sessionData.put("participants", participants);
        return sessionData;
    }

    @DeleteMapping("/presenter/{sessionId}")
    public void deleteSession(@PathVariable("sessionId") Integer sessionId) {
        sessionManager.checkSessionId(sessionId);

        //Session session = sessionManager.getSession(sessionId).orElseThrow(NoSessionFoundException::new);

    }

    @PostMapping("/{sessionId}/question/create")
    public QuestionModel createQuestion(@RequestBody QuestionModel questionModel, @PathVariable("sessionId") Integer sessionId){
        sessionManager.checkSessionId(sessionId);

        sessionManager.getSessionById(sessionId).orElseThrow(NoSessionFoundException::new).addQuestion(questionModel);

        //sendToAll
        questionService.sendQuestion(questionModel,sessionId);

        return questionModel;

    }

    //TODO: LFD-79 //Aktuell Testdaten
    @GetMapping("/presenter/sessions/metadata")
    public List<SessionMetadata> getSessionsMetadata() {

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
