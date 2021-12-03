package com.lecturefeed.session;

import com.lecturefeed.socket.controller.service.SessionDataService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class SessionManager {
    private final HashMap<Integer,Session> sessions = new HashMap<>();

    @Getter
    private final SessionDataService sessionDataService;

    public Session createSession(){
        int sessionId = sessions.size() +1;

        Session session = new Session(sessionDataService, sessionId);
        sessions.put(sessionId,session);
        return session;
    }

    public boolean isCorrectSessionCode(Integer sessionId, String sessionCode){

        return getSession(sessionId).
                map(Session::getSessionCode).
                map(sessionCode::equals).
                orElse(false);
    }

    public Optional<Session> getSession(Integer id){
        return Optional.ofNullable(sessions.get(id));
    }
}
