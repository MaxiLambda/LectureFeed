package com.lecturefeed.manager;

import com.lecturefeed.entity.model.Session;
import com.lecturefeed.entity.model.survey.Survey;
import com.lecturefeed.entity.model.survey.SurveyAnswer;
import com.lecturefeed.entity.model.survey.SurveyTemplate;
import com.lecturefeed.model.survey.SurveyTimer;
import com.lecturefeed.repository.service.SurveyAnswerDBService;
import com.lecturefeed.repository.service.SurveyDBService;
import com.lecturefeed.socket.controller.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SurveyManager {


    private final SurveyService surveyService;
    private final SessionManager sessionManager;
    private final SurveyDBService surveyDBService;
    private final SurveyAnswerDBService surveyAnswerDBService;
    private final Set<Integer> sessionHasActiveSurvey = new HashSet<>();

    public Collection<Survey> getSurveysBySessionId(int sessionId){
        return sessionManager.getSessionById(sessionId).getSurveys();
    }

    public SurveyTemplate createSurvey(int sessionId, SurveyTemplate template){
        if(sessionHasActiveSurvey.contains(sessionId)) throw new RuntimeException("Session already has an active Survey");

        Survey survey = Survey.builder().template(template).build();
        sessionManager.getSessionById(sessionId).getSurveys().add(survey);
        surveyDBService.save(survey);

        surveyService.onCreateByAdmin(sessionId, survey);
        surveyService.onCreateByParticipant(sessionId, survey.getId(), template);

        sessionHasActiveSurvey.add(sessionId);

        //start Thread to publish survey after a given amount of time
        new SurveyTimer(sessionId, survey.getId(), surveyService, this).start();

        return template;
    }

    public void updateSurvey(Survey survey){
        surveyDBService.save(survey);
    }

    public void addAnswerToSurvey(int sessionId, int surveyId,int participantId, String answer){
        Survey survey = getSurveyById(surveyId);
        if(survey != null){
            this.addAnswerToSurvey(survey, participantId, answer);
            surveyService.onUpdate(sessionId, getSurveyById(surveyId));
        }
    }

    private void addAnswerToSurvey(Survey survey, int participantId, String answerValue){
        boolean noneMatch = survey.getSurveyAnswers()
                .stream()
                .noneMatch(a -> a.getParticipantId().equals(participantId));
        if(noneMatch){
            SurveyAnswer surveyAnswer = SurveyAnswer.builder()
                    .survey(survey).participantId(participantId).value(answerValue).build();
            surveyAnswerDBService.save(surveyAnswer);
            survey.getSurveyAnswers().add(surveyAnswer);
            surveyDBService.save(survey);
        }
    }

    public Survey getSurveyById(int id){
        return surveyDBService.findById(id);
    }

    public void closeSurvey(int sessionId){
        sessionHasActiveSurvey.remove(sessionId);
    }

}
