package com.lecturefeed.model.survey;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//TODO: add @Entity
@Getter
@Setter
public class SurveyEntity extends SurveyTemplateEntity{
    private ArrayList<String> answers;
    private Set<Integer> answeredUserId;
    //the time when the survey was started
    private long timestamp;

    public SurveyEntity(SurveyType surveyType, String question, long duration, boolean publishResults, int id, ArrayList<String> answers, Set<Integer> answeredUserId, long timestamp) {
        super(surveyType, question, duration, publishResults, id);
        this.answers = answers;
        this.answeredUserId = answeredUserId;
        this.timestamp = timestamp;
    }

    public SurveyEntity(SurveyTemplateEntity surveyTemplateEntity,int id, ArrayList<String> answers, Set<Integer> answeredUserId, long timestamp) {
        super(
                surveyTemplateEntity.getSurveyType(),
                surveyTemplateEntity.getQuestion(),
                surveyTemplateEntity.getDuration(),
                surveyTemplateEntity.isPublishResults(),
                id
        );

        this.answers = answers;
        this.answeredUserId = answeredUserId;
        this.timestamp = timestamp;
    }

    public SurveyEntity(SurveyCreationModel surveyCreationModel) {
        new SurveyEntity(surveyCreationModel.getSurveyTemplateEntity(), surveyCreationModel.getId(), new ArrayList<>(), new HashSet<>(),System.currentTimeMillis());
    }

    public void answerSurvey(String answer, Integer answeringUserId){
        if(!answeredUserId.contains(answeringUserId)){
            answers.add(answer);
        }
    }
}
