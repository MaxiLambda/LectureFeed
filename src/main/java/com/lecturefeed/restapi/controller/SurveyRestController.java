package com.lecturefeed.restapi.controller;

import com.lecturefeed.authentication.jwt.TokenService;
import com.lecturefeed.model.MessageModel;
import com.lecturefeed.model.survey.Survey;
import com.lecturefeed.model.survey.SurveyTemplate;
import com.lecturefeed.model.survey.SurveyTimer;
import com.lecturefeed.session.SessionManager;
import com.lecturefeed.socket.controller.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SurveyRestController {

    private final SurveyService surveyService;
    private final SessionManager sessionManager;
    private final TokenService tokenService;

    private final HashMap<Integer, SurveyTemplate> templates = new HashMap<>();
    private final HashMap<Integer, HashMap<Integer, Survey>> sessionSurveys = new HashMap<>();


    @GetMapping("/admin/session/{sessionId}/survey/templates")
    public Collection<SurveyTemplate> getSessionTemplates(@PathVariable int sessionId){
        sessionManager.checkSessionId(sessionId);
        return templates.values();
    }

    @GetMapping("/admin/session/{sessionId}/surveys")
    public Collection<Survey> getSessionSurveys(@PathVariable int sessionId){
        sessionManager.checkSessionId(sessionId);
        Collection<Survey> surveys = getSessionSurveyList(sessionId).values();
        if(surveys == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Surveys not exists", surveys));
        }
        return getSessionSurveyList(sessionId).values();
    }

    @GetMapping("/admin/session/{sessionId}/survey/create/{templateId}")
    public void create(@PathVariable int sessionId, @PathVariable int templateId){
        sessionManager.checkSessionId(sessionId);
        SurveyTemplate template = getTemplateById(templateId);
        if(template != null){
            createSurveyInSession(sessionId, template);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("SurveyTemplate-ID %d not exists", templateId));
        }
    }

    @PostMapping("/admin/session/{sessionId}/survey/create")
    public SurveyTemplate create(@PathVariable int sessionId, @RequestBody SurveyTemplate template){
        sessionManager.checkSessionId(sessionId);
        template.setId(templates.size()+1);
        templates.put(template.getId(), template);
        createSurveyInSession(sessionId, template);
        return template;
    }

    @PostMapping("/participant/session/{sessionId}/survey/{surveyId}/answer")
    public void setAnswer(@PathVariable int sessionId, @PathVariable int surveyId, @RequestBody MessageModel messageModel, @RequestHeader("Authorization") String token){
        tokenService.checkSessionIdByToken(token, sessionId);
        sessionManager.checkSessionId(sessionId);

        Survey survey = getSessionSurveyList(sessionId).get(surveyId);
        if(survey != null){
            survey.addAnswer(messageModel.getText());
            surveyService.onUpdate(sessionId, survey);
        }
    }

    private SurveyTemplate getTemplateById(int templateId){
        return templates.get(templateId);
    }

    //removes the need to check if sessionSurveys returns null for a given key
    private HashMap<Integer, Survey> getSessionSurveyList(int sessionId){
        if(!sessionSurveys.containsKey(sessionId)) sessionSurveys.put(sessionId, new HashMap<>());
        return sessionSurveys.get(sessionId);
    }

    private void addSurveyToSession(int sessionId, Survey survey){
        if(!sessionSurveys.containsKey(sessionId)) sessionSurveys.put(sessionId, new HashMap<>());
        sessionSurveys.get(sessionId).put(survey.getId(), survey);
    }

    public void updateSurvey(int sessionId, Survey survey){
        sessionSurveys.get(sessionId).put(survey.getId(), survey);
    }

    private SurveyTemplate createSurveyInSession(int sessionId, SurveyTemplate template){
        //create the new SurveyEntity
        Survey survey = new Survey(getSessionSurveyList(sessionId).size()+1, template, new ArrayList<>(),System.currentTimeMillis());
        //add it to the surveys in the Session with id sessionId
        addSurveyToSession(sessionId, survey);

        surveyService.onCreateByAdmin(sessionId, survey);
        surveyService.onCreateByParticipant(sessionId, survey.getId(), template);

        //start Thread to publish survey after a given amount of time
        new SurveyTimer(sessionId, survey, surveyService, this).start();

        return template;
    }
}
