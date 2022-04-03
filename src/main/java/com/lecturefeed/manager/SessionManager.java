package com.lecturefeed.manager;

import com.lecturefeed.entity.model.Session;
import com.lecturefeed.entity.model.SessionMetadata;
import com.lecturefeed.repository.service.SessionDBService;
import com.lecturefeed.socket.controller.service.SessionDataService;
import com.lecturefeed.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SessionManager {

    private final SessionDataService sessionDataService;
    private final SessionDBService sessionDBService;
    private static final int SESSION_CODE_LENGTH = 8;

    public boolean isSessionClosed(int sessionId){
        return getSessionById(sessionId).getClosed() != 0L;
    }

    private Session createSessionEntity(String name){
        return Session.builder().name(name).sessionCode(StringUtils.randomString(SESSION_CODE_LENGTH)).build();
    }

    public Session createSession(String name){
        Session session = createSessionEntity(name);
        sessionDBService.save(session);
        return session;
    }

    public SessionMetadata toMetadata(Session session){
        return new SessionMetadata(session.getId(),session.getName(),session.getClosed());
    }

    public boolean isCorrectSessionCode(Integer sessionId, String sessionCode){
        Session session = getSessionById(sessionId);
        return session != null && session.getSessionCode().equals(sessionCode);
    }

    public Session getSessionById(Integer id) {
        return sessionDBService.findById(id);
    }

    public Set<Integer> getAllSessionIds(){
        return sessionDBService.findAll()
                .stream()
                .map(Session::getId)
                .collect(Collectors.toSet());
    }


    public void closeSession(int sessionId){
        Session session = getSessionById(sessionId);
        session.setClosed(new Date().getTime());
        sessionDataService.sendClose(sessionId);
        sessionDBService.save(session);
    }


    public void checkSessionId(int sessionId, boolean closedAllowed){
        Session session = getSessionById(sessionId);
        if (session == null || closedAllowed && session.getClosed() != 0L)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("SessionId %d are not exists", sessionId));
    }

    public void checkSessionId(int sessionId){
        this.checkSessionId(sessionId, false);
    }

    public boolean existsSessionId(int sessionId){
        return getSessionById(sessionId) != null;
    }

    public void deleteSession(int sessionId){
        sessionDBService.deleteById(sessionId);
    }

    public List<Session> getAllSessions(){
        return sessionDBService.findAll();
    }

    public List<Session> getAllOpenSessions(){
        return sessionDBService.findAllOpen();
    }

    public List<Session> getAllClosedSessions(){
        return sessionDBService.findAllClosed();
    }

    public Session saveSession(Session session){
        return sessionDBService.save(session);
    }

    public void closeAllOpenSessions(){
        sessionDBService.findAllOpen().stream().forEach(session ->  closeSession(session.getId()));
    }



}
