package com.lecturefeed.session;

import com.lecturefeed.socket.controller.service.SessionDataService;
import lombok.*;

import java.util.ArrayList;
import java.util.Optional;

@Data
public class Session {
    private final SessionDataService sessionDataService;
    private final ArrayList<Participant> participants;
    private final int id;
    private final String sessionCode;
    private int userIdCurrentIndex = 0;

    public Session(SessionDataService sessionDataService, Integer id){
        this.sessionDataService = sessionDataService;
        this.participants = new ArrayList<>();
        this.id = id;
        //todo replace with actual generation (Random Number generator)
        this.sessionCode = id+"code";
    }

    public Integer getNextUserId(){
        return ++userIdCurrentIndex;
    }

    public Optional<Participant> getParticipant(Integer participantId){
        return Optional.ofNullable(participants.get(participantId));
    }

    public void addParticipant(Participant participant){
        participants.add(participant);
        sessionDataService.sendNewParticipantToAll(id,participant);
    }

}
