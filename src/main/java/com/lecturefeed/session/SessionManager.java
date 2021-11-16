package com.lecturefeed.session;

import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import com.lecturefeed.socket.controller.service.SessionDataService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
//@RequiredArgsConstructor
public class SessionManager {
    private final HashMap<Integer,Session> sessions = new HashMap<>();

    @Getter
    private final SessionDataService sessionDataService;

    //todo remove Constructor
    public SessionManager(SessionDataService sessionDataService){
        this.sessionDataService = sessionDataService;
        int newSessionId = createSession();
        System.out.printf("Session with Id %d created!%n", newSessionId);
    }

    public Integer createSession(){
        int sessionId = sessions.size()+1;
        Session session = new Session(sessionDataService, sessionId);
        sessions.put(sessionId,session);
        return sessionId;
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
