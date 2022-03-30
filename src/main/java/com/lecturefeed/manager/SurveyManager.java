package com.lecturefeed.manager;

import com.lecturefeed.model.survey.Survey;
import com.lecturefeed.model.survey.SurveyTemplate;
import com.lecturefeed.model.survey.SurveyTimer;
import com.lecturefeed.socket.controller.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class SurveyManager {

    private final SurveyService surveyService;
    private final SessionManager sessionManager;

    public Collection<Survey> getSurveysBySessionId(int sessionId){
        return sessionManager.getSessionById(sessionId).getSurveys().values();
    }

    public SurveyTemplate createSurvey(int sessionId, SurveyTemplate template){
        Survey survey = new Survey(getSurveysBySessionId(sessionId).size()+1, template, new ArrayList<>(), System.currentTimeMillis());
        sessionManager.getSessionById(sessionId).getSurveys().put(survey.getId(), survey);

        surveyService.onCreateByAdmin(sessionId, survey);
        surveyService.onCreateByParticipant(sessionId, survey.getId(), template);

        //start Thread to publish survey after a given amount of time
        new SurveyTimer(sessionId, survey, surveyService, this).start();

        return template;
    }

    public void updateSurvey(int sessionId, Survey survey){
        sessionManager.getSessionById(sessionId).getSurveys().put(survey.getId(), survey);
    }

    public void addAnswerToSurvey(int sessionId, int surveyId, String answer){
        Survey survey = sessionManager.getSessionById(sessionId).getSurveys().get(surveyId);
        if(survey != null){
            survey.addAnswer(answer);
            surveyService.onUpdate(sessionId, survey);
        }
    }

}
