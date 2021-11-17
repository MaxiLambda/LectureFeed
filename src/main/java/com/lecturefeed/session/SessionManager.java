package com.lecturefeed.session;

import com.lecturefeed.authentication.jwt.CustomAuthenticationService;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.UserRole;
import com.lecturefeed.socket.controller.service.SessionDataService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class SessionManager {
    private final HashMap<Integer,Session> sessions = new HashMap<>();

    @Getter
    private final SessionDataService sessionDataService;

    //todo remove Constructor
//    public SessionManager(SessionDataService sessionDataService){
//        this.sessionDataService = sessionDataService;
//        int newSessionId = createSession();
//        System.out.printf("Session with Id %d created!%n", newSessionId);
//    }

    public Session createSession(){
        Random random = new Random();
        int possibleId;
        do{
            possibleId = random.nextInt();
        }while(sessions.get(possibleId) == null);

        Session session = new Session(sessionDataService, possibleId);
        sessions.put(possibleId,session);
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
