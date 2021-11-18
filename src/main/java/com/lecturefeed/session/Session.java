package com.lecturefeed.session;

import com.lecturefeed.socket.controller.service.SessionDataService;
import lombok.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Data
public class Session {
    private final SessionDataService sessionDataService;
    private final ArrayList<Participant> participants;
    private final ArrayList<Question> questions;
    private final int id;
    private final String sessionCode;
    private int userIdCurrentIndex = 0;
    private final int SESSION_CODE_LENGTH = 8;

    public Session(SessionDataService sessionDataService, ArrayList<Question> questions, Integer id){
        this.sessionDataService = sessionDataService;
        this.questions = questions;
        this.participants = new ArrayList<>();
        this.id = id;
        //todo replace with actual generation (Random Number generator)
        this.sessionCode = generateRandomStringCode();
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

    private String generateRandomStringCode()
    {
        byte[] array = new byte[SESSION_CODE_LENGTH];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
