package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.TokenService;
import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.entity.model.Question;
import com.lecturefeed.entity.model.Session;
import com.lecturefeed.entity.model.SessionMetadata;
import com.lecturefeed.manager.ParticipantManager;
import com.lecturefeed.manager.QuestionManager;
import com.lecturefeed.manager.SessionManager;
import com.lecturefeed.model.*;
import com.lecturefeed.utils.SecurityContextHolderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/session")
public class SessionRestController {

    private final SessionManager sessionManager;
    private final QuestionManager questionManager;
    private final ParticipantManager participantManager;
    private final TokenService tokenService;

    @PostMapping("/presenter/create")
    public Map<String,Object> createNewSession(@RequestBody CreateSessionModel createSessionModel) {
        Session session = sessionManager.createSession(createSessionModel.getName());
        Map<String,Object> sessionInformation = new HashMap<>();
        sessionInformation.put("id",session.getId());
        sessionInformation.put("sessionCode",session.getSessionCode());
        return sessionInformation;
    }

    @GetMapping("/presenter/{sessionId}/close")
    public void closeSession(@PathVariable("sessionId") Integer sessionId) {
        sessionManager.checkSessionId(sessionId);
        if(!sessionManager.isSessionClosed(sessionId))
            sessionManager.closeSession(sessionId);
    }

    @GetMapping("/{sessionId}/initial")
    public Map<String,Object> getSessionData(@PathVariable("sessionId") Integer sessionId, @RequestHeader("Authorization") String token) {
        if(!SecurityContextHolderUtils.isCurrentUserAdmin()) tokenService.checkSessionIdByToken(token, sessionId);
        sessionManager.checkSessionId(sessionId);

        Session session = sessionManager.getSessionById(sessionId);
        Set<Participant> participants = session.getParticipants();
        Set<Question> questions = session.getQuestions();

        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("questions", questions);
        sessionData.put("participants", participants);
        return sessionData;
    }

    @DeleteMapping("/presenter/{sessionId}")
    public void deleteSession(@PathVariable("sessionId") Integer sessionId) {
        sessionManager.checkSessionId(sessionId);
        if(sessionManager.isSessionClosed(sessionId)) sessionManager.deleteSession(sessionId);
    }

    @PostMapping("/{sessionId}/question/create")
    public Question createQuestion(@RequestBody QuestionModel questionModel, @PathVariable("sessionId") Integer sessionId){
        sessionManager.checkSessionId(sessionId);
        participantManager.checkParticipantId(questionModel.getParticipantId());
        return questionManager.createQuestionByModel(sessionId, questionModel);
    }

    //TODO: LFD-79 //Aktuell Testdaten
    @GetMapping("/presenter/sessions/metadata")
    public List<SessionMetadata> getSessionsMetadata() {
        return sessionManager.getAllClosedSessions()
                .stream()
                .map(sessionManager::toMetadata)
                .collect(Collectors.toList());
    }

    //TODO: LFD-79 //Aktuell Testdaten
    @GetMapping("/presenter/{sessionId}/data")
    public Session getSessionData(@PathVariable("sessionId") Integer sessionId) {
        sessionManager.checkSessionId(sessionId);
        return sessionManager.getSessionById(sessionId);
    }

}
