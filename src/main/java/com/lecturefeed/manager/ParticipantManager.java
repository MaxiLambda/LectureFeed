package com.lecturefeed.manager;

import com.lecturefeed.model.Participant;
import com.lecturefeed.model.Session;
import com.lecturefeed.socket.controller.service.SessionDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParticipantManager {

    private final SessionManager sessionManager;
    private final SessionDataService sessionDataService;

    public Participant createParticipantBySessionId(Integer sessionId, String nickname){
        Session session = sessionManager.getSessionById(sessionId);
        if(session != null){
            Participant participant = new Participant(session.getParticipants().size() + 1, nickname);
            session.addParticipant(participant);
            sessionDataService.sendNewParticipantToAll(sessionId, participant);
            return participant;
        }
        return null;
    }
}
