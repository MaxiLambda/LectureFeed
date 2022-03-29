package com.lecturefeed.session;

import com.lecturefeed.authentication.AuthenticatorService;
import com.lecturefeed.socket.controller.service.SessionDataService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

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

        return getSessionById(sessionId).
                map(Session::getSessionCode).
                map(sessionCode::equals).
                orElse(false);
    }

    public Optional<Session> getSessionById(Integer id) {
        return Optional.ofNullable(sessions.get(id));
    }

    public Set<Integer> getAllSessionIds(){
        return sessions.keySet();
    }

    public void checkSessionId(int sessionId){
        if (getSessionById(sessionId).isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("SessionId %d are not exists", sessionId));
    }



}
