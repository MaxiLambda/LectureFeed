package com.lecturefeed.session;

import lombok.*;

import java.util.ArrayList;
import java.util.Optional;

@Data
public class Session {
    private final SessionManager sessionManager;
    private final ArrayList<Participant> participants;
    private final int id;
    private final String sessionCode;
    private int userIdCurrentIndex = 0;

    public Session(SessionManager sessionManager, Integer id){
        this.sessionManager = sessionManager;
        this.participants = new ArrayList<>();
        this.id = id;
        //todo replace with actual generation
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
        sessionManager.getSessionDataService().sendNewParticipantToAll(id,participant);
    }

}
