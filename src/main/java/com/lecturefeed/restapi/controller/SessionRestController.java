package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.TokenService;
import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.entity.model.Question;
import com.lecturefeed.entity.model.Session;
import com.lecturefeed.entity.model.SessionMetadata;
import com.lecturefeed.manager.CSVManager;
import com.lecturefeed.manager.ParticipantManager;
import com.lecturefeed.manager.QuestionManager;
import com.lecturefeed.manager.SessionManager;
import com.lecturefeed.model.*;
import com.lecturefeed.socket.controller.core.WebSocketHolderService;
import com.lecturefeed.utils.SecurityContextHolderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/session")
public class SessionRestController {

    private final SessionManager sessionManager;
    private final QuestionManager questionManager;
    private final ParticipantManager participantManager;
    private final TokenService tokenService;
    private final WebSocketHolderService webSocketHolderService;
    private final CSVManager csvManager;

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

        if(sessionManager.isSessionClosed(sessionId)){
            return null;
        }

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

    @GetMapping("/presenter/{sessionId}/data")
    public Session getSessionData(@PathVariable("sessionId") Integer sessionId) {
        sessionManager.checkSessionId(sessionId, false);
        return sessionManager.getSessionById(sessionId);
    }

    @GetMapping("/presenter/{sessionId}/participant/{participantId}/kill/{blocked}")
    public void killParticipant(@PathVariable("sessionId") Integer sessionId, @PathVariable("participantId") Integer participantId, @PathVariable("blocked") Boolean blocked) {
        sessionManager.checkSessionId(sessionId);
        participantManager.checkParticipantId(participantId);
        if(blocked){
            webSocketHolderService.blockRemoteAddrByParticipantId(participantId);
        }
        webSocketHolderService.killConnectionByParticipantId(participantId);
    }

    //TODO: LFD-92 //Aktuell Testdaten
    @GetMapping("/presenter/{sessionId}/data/download")
    public FileSystemResource downloadSessionData(@PathVariable("sessionId") Integer sessionId) {
        sessionManager.checkSessionId(sessionId);
        try {
              File tempFile = csvManager.buildSessionZip(sessionId);
            return new FileSystemResource(tempFile);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Failed to create data by session id %d", sessionId));
        }
    }

}
