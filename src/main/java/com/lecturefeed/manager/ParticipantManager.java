package com.lecturefeed.manager;

import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.entity.model.Session;
import com.lecturefeed.repository.service.ParticipantDBService;
import com.lecturefeed.socket.controller.service.SessionDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ParticipantManager {

    private final SessionManager sessionManager;
    private final SessionDataService sessionDataService;
    private final ParticipantDBService participantDBService;

    public Participant createParticipantBySessionId(Integer sessionId, String nickname){
        Session session = sessionManager.getSessionById(sessionId);
        if(session != null){
            Participant participant = Participant.builder().nickname(nickname).session(session).build();
            participantDBService.save(participant);
            session.getParticipants().add(participant);
            sessionManager.saveSession(session);
            sessionDataService.sendNewParticipantToAll(sessionId, participant);
            return participant;
        }
        return null;
    }

    public Session getSessionByParticipantId(int participantId){
        Participant participant = participantDBService.findById(participantId);
        if(participant != null){
            return participant.getSession();
        }
        return null;
    }

    public Integer getSessionIdByParticipantId(int participantId){
        Session session = getSessionByParticipantId(participantId);
        if(session != null) return session.getId();
        return null;
    }

    public List<Participant> getParticipantsBySessionId(Integer sessionId){
        return participantDBService.findBySession(sessionManager.getSessionById(sessionId));
    }

    public List<Participant> getConnectedParticipantsBySessionId(Integer sessionId){
        return getParticipantsBySessionId(sessionId).stream().filter(Participant::isConnected).toList();
    }

    public void updateConnectionStatusByParticipantId(Integer participant_id, Boolean status){
        Participant participant = participantDBService.findById(participant_id);
        if(participant != null){
            participant.setConnected(status);
            participantDBService.save(participant);
        }
    }

    public void checkParticipantId(Integer participantId){
        if (participantId == null || participantDBService.findById(participantId)==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Participant-Id %d are not exists", participantId));
    }

    public boolean existsParticipantId(int participantId){
        return participantDBService.findById(participantId)!=null;
    }

}
