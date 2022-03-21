package com.lecturefeed.model.survey;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//TODO: add @Entity
@Getter
@Setter
public class Survey {
    private int id;//TODO in the specification this is in the SurveyTemplateEntity Class
    private List<String> answers;
    private SurveyTemplate template;
    //the time when the survey was started
    private long timestamp;

    public Survey(int id, SurveyTemplate template, List<String> answers, long timestamp) {
        this.id = id;
        this.template = template;
        this.answers = answers;
        this.timestamp = timestamp;
    }

    public Survey(int id, SurveyTemplate template) {
        this.id = id;
    }
}
