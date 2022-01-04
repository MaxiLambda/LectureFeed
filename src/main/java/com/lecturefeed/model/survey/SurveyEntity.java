package com.lecturefeed.model.survey;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

//TODO: add @Entity
@Getter
@Setter
public class SurveyEntity extends SurveyTemplateEntity{
    private int id;//TODO in the specification this is in the SurveyTemplateEntity Class
    private ArrayList<String> answers;
    //the time when the survey was started
    private long timestamp;

    public SurveyEntity(SurveyType surveyType, String question, long duration, boolean publishResults, int id, ArrayList<String> answers, long timestamp) {
        super(surveyType, question, duration, publishResults);
        this.id = id;
        this.answers = answers;
        this.timestamp = timestamp;
    }

    public SurveyEntity(SurveyTemplateEntity surveyTemplateEntity,int id, ArrayList<String> answers, long timestamp) {
        super(
                surveyTemplateEntity.getSurveyType(),
                surveyTemplateEntity.getQuestion(),
                surveyTemplateEntity.getDuration(),
                surveyTemplateEntity.isPublishResults()
        );
        this.id = id;
        this.answers = answers;
        this.timestamp = timestamp;
    }

    public SurveyEntity(SurveyCreationModel surveyCreationModel, ArrayList<String> answers, long timestamp) {
        new SurveyEntity(surveyCreationModel.getSurveyTemplateEntity(), surveyCreationModel.getId(), answers, timestamp);
    }
}
