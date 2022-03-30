package com.lecturefeed.model;

import com.lecturefeed.model.survey.Survey;
import com.lecturefeed.utils.StringUtils;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Data
public class Session {
    private final List<Participant> participants;
    @Getter
    private final HashMap<Integer, QuestionModel> questions;
    @Getter
    private final HashMap<Integer, Survey> surveys;
    private final int id;
    private long closed = 0;
    private final String sessionCode;
    private final int SESSION_CODE_LENGTH = 8;

    public Session(Integer id){
        this.id = id;
        this.sessionCode = StringUtils.randomString(SESSION_CODE_LENGTH);
        this.participants = new ArrayList<>();
        this.questions = new HashMap<>();
        this.surveys = new HashMap<>();
    }

    public Session(Integer id, String sessionCode, List<Participant> participants, HashMap<Integer, QuestionModel> questions, HashMap<Integer, Survey> surveys){
        this.id = id;
        this.sessionCode = sessionCode;
        this.participants = participants;
        this.questions = questions;
        this.surveys = surveys;
    }

    public void addParticipant(Participant participant){
        participants.add(participant);
    }




}
