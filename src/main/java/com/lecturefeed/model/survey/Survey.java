package com.lecturefeed.model.survey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//TODO: add @Entity
@Getter
@Setter
public class Survey {
    private int id;//TODO in the specification this is in the SurveyTemplateEntity Class
    private List<String> answers;
    private SurveyTemplate template;
    //the time when the survey was started
    private long timestamp;
    //@Transient should prevent answeredIds from being persisted in the DB and from being returned as part of the Survey-Json Object
    @Transient
    private Set<Integer> answeredIds = new HashSet<>();
    public Survey(int id, SurveyTemplate template, List<String> answers, long timestamp) {
        this.id = id;
        this.template = template;
        this.answers = answers;
        this.timestamp = timestamp;
    }

    public Survey(int id, SurveyTemplate template) {
        this.id = id;
    }

    public void addAnswer(String answer, int answeringId){
        //TODO throw Exception if id already answered?
        if(answeredIds.add(answeringId)) answers.add(answer);
    }
}
