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
    @Autowired
    @Getter
    private final SessionDataService sessionDataService;

    //todo remove Constructor
    public SessionManager(SessionDataService sessionDataService){
        this.sessionDataService = sessionDataService;
        int newSessionId = createSession();
        System.out.printf("Session with Id %d created!%n", newSessionId);
    }

    public Integer createSession(){
        int sessionId = sessions.size();
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

    public TokenModel createToken(CustomAuthenticationService customAuthenticationService, String nickname, UserRole role, Integer sessionId){
        Map<String, Object> payloadClaims = new HashMap<>();
        int id = getSession(sessionId).
                map(Session::getNextUserId).
                orElseThrow();
        payloadClaims.put("id",id);
        payloadClaims.put("username", nickname);
        payloadClaims.put("role", role.getRole());
        payloadClaims.put("sessionId", sessionId);
        return new TokenModel(customAuthenticationService.generateToken(payloadClaims),id);
    }
}
