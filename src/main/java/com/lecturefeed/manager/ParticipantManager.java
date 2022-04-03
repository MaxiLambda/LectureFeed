package com.lecturefeed.manager;

import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.entity.model.Session;
import com.lecturefeed.repository.service.ParticipantDBService;
import com.lecturefeed.repository.service.SurveyTemplateDBService;
import com.lecturefeed.socket.controller.service.SessionDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return participantDBService.findById(participantId).getSession();
    }

    public int getSessionIdByParticipantId(int participantId){
        return getSessionByParticipantId(participantId).getId();
    }

    public List<Participant> getParticipantsBySessionId(Integer sessionId){
        return participantDBService.findBySession(sessionManager.getSessionById(sessionId));
    }

    public List<Participant> getConnectedParticipantsBySessionId(Integer sessionId){
        return getParticipantsBySessionId(sessionId).stream().filter(Participant::isConnected).toList();
    }

    public void updateConnectionStatusByParticipantId(Integer participant_id, Boolean status){
        Participant participant = participantDBService.findById(participant_id);
        participant.setConnected(status);
        participantDBService.save(participant);
    }

    public void checkParticipantId(int participantId){
        if (participantDBService.findById(participantId)==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Participant-Id %d are not exists", participantId));
    }

    public boolean existsParticipantId(int participantId){
        return participantDBService.findById(participantId)!=null;
    }

}
