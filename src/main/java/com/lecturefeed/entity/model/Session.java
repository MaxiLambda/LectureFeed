package com.lecturefeed.entity.model;

import com.lecturefeed.entity.model.survey.Survey;
import com.lecturefeed.utils.StringUtils;
import lombok.*;

import javax.persistence.*;
import java.util.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {
    private String name;

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "session_id")
    private Set<Participant> participants = Set.of();

    @Builder.Default
    @Getter
    @OneToMany
    @JoinColumn(name = "session_id")
    private Set<QuestionModel> questions = Set.of();

    @Builder.Default
    @Getter
    @OneToMany
    @JoinColumn(name = "session_id")
    private Set<Survey> surveys = Set.of();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private long closed = 0;

    private String sessionCode;
    private final int SESSION_CODE_LENGTH = 8;

    public Session(Integer id, String name){
        this.id = id;
        this.name = name;
        this.sessionCode = StringUtils.randomString(SESSION_CODE_LENGTH);
        this.participants = new HashSet<>();
        this.questions = new HashSet<>();
        this.surveys = new HashSet<>();
    }

    public Session(Integer id, String name, String sessionCode, HashSet<Participant> participants, HashSet<QuestionModel> questions, HashSet<Survey> surveys){
        this.id = id;
        this.name = name;
        this.sessionCode = sessionCode;
        this.participants = participants;
        this.questions = questions;
        this.surveys = surveys;
    }

    public void addParticipant(Participant participant){
        participants.add(participant);
    }




}
