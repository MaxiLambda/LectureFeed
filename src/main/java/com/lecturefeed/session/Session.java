package com.lecturefeed.session;

import com.lecturefeed.model.QuestionModel;
import com.lecturefeed.socket.controller.service.SessionDataService;
import com.lecturefeed.utils.StringUtils;
import lombok.Data;
import java.util.ArrayList;
import java.util.Optional;


@Data
public class Session {
    private final SessionDataService sessionDataService;
    private final ArrayList<Participant> participants;
    private final ArrayList<QuestionModel> questions;
    private final int id;
    private final String sessionCode;
    private int userIdCurrentIndex = 0;
    private final int SESSION_CODE_LENGTH = 8;

    public Session(SessionDataService sessionDataService, Integer id){
        this.sessionDataService = sessionDataService;
        this.questions = new ArrayList<>();
        this.participants = new ArrayList<>();
        this.id = id;
        this.sessionCode = StringUtils.randomString(SESSION_CODE_LENGTH);
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

    //returns question Id
    public void addQuestion(QuestionModel question){
        questions.add(question);
        int questionId = questions.indexOf(question);
        question.setId(questionId);
    }
}
