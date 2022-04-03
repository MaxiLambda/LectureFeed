package com.lecturefeed.manager;

import com.lecturefeed.entity.model.Session;
import com.lecturefeed.entity.model.survey.Survey;
import com.lecturefeed.entity.model.survey.SurveyTemplate;
import com.lecturefeed.model.survey.SurveyTimer;
import com.lecturefeed.repository.service.SurveyDBService;
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
    private final SurveyDBService surveyDBService;

    public Collection<Survey> getSurveysBySessionId(int sessionId){
        return sessionManager.getSessionById(sessionId).getSurveys();
    }

    public SurveyTemplate createSurvey(int sessionId, SurveyTemplate template){
        Survey survey = Survey.builder().template(template).build();
        sessionManager.getSessionById(sessionId).getSurveys().add(survey);
        surveyDBService.save(survey);

        surveyService.onCreateByAdmin(sessionId, survey);
        surveyService.onCreateByParticipant(sessionId, survey.getId(), template);

        //start Thread to publish survey after a given amount of time
        new SurveyTimer(sessionId, survey.getId(), surveyService, this).start();

        return template;
    }

    public void updateSurvey(int sessionId, Survey survey){
        Session session = sessionManager.getSessionById(sessionId);
        session.getSurveys().add(survey);
        sessionManager.saveSession(session);
    }

    public void addAnswerToSurvey(int sessionId, int surveyId,int participantId, String answer){
        Survey survey = getSurveyById(surveyId);
        if(survey != null){
            survey.addAnswer(participantId, answer);
            surveyDBService.save(survey);
            surveyService.onUpdate(sessionId, survey);
        }
    }

    public Survey getSurveyById(int id){
        return surveyDBService.findById(id);
    }

}
