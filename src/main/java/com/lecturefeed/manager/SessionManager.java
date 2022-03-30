package com.lecturefeed.manager;

import com.lecturefeed.model.Session;
import com.lecturefeed.socket.controller.service.SessionDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SessionManager {

    private final SessionDataService sessionDataService;

    private final HashMap<Integer, Session> sessions = new HashMap<>();

    public Session createSession(String name){
        int sessionId = sessions.size() + 1;
        Session session = new Session(sessionId, name);
        sessions.put(sessionId,session);
        return session;
    }

    public boolean isCorrectSessionCode(Integer sessionId, String sessionCode){
        Session session = getSessionById(sessionId);
        return session != null && session.getSessionCode().equals(sessionCode);
    }

    public Session getSessionById(Integer id) {
        if(!sessions.containsKey(id)) return null;
        return sessions.get(id);
    }

    public Set<Integer> getAllSessionIds(){
        return sessions.keySet();
    }


    public void closeSession(int sessionId){
        Session session = getSessionById(sessionId);
        session.setClosed(new Date().getTime());
        sessionDataService.sendClose(sessionId);
    }


    public void checkSessionId(int sessionId){
        if (getSessionById(sessionId) == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("SessionId %d are not exists", sessionId));
    }

    public boolean existsSessionId(int sessionId){
        return sessions.containsKey(sessionId);
    }



}
