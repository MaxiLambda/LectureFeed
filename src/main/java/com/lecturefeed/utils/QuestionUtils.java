package com.lecturefeed.utils;

import com.lecturefeed.entity.model.Participant;
import com.lecturefeed.entity.model.Session;
import lombok.Getter;


public class QuestionUtils {
    public final static Session HIDDEN_SESSION  = Session.builder()
            .id(1)
            .name("hidden")
            .sessionCode("")
            .build();
    public final static Participant HIDDEN_PARTICIPANT = Participant.builder()
            .id(1)
            .session(HIDDEN_SESSION)
            .nickname("hidden")
            .connected(true)
            .build();
}
