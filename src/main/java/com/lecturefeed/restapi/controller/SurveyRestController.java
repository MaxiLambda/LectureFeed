package com.lecturefeed.restapi.controller;

import com.lecturefeed.model.MessageModel;
import com.lecturefeed.model.TokenModel;
import com.lecturefeed.model.survey.Survey;
import com.lecturefeed.model.survey.SurveyManager;
import com.lecturefeed.model.survey.SurveyTemplate;
import com.lecturefeed.model.survey.SurveyTimer;
import com.lecturefeed.model.survey.SurveyType;
import com.lecturefeed.socket.controller.service.SurveyService;
import com.lecturefeed.utils.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@RequiredArgsConstructor
@RestController
public class SurveyRestController {

    private final SurveyService surveyService;
    private final SurveyManager surveyManager;
    private final SessionManager sessionManager;
    private final TokenService tokenService;

    @GetMapping("/admin/session/{sessionId}/survey/templates")
    public Collection<SurveyTemplate> getSessionTemplates(@PathVariable int sessionId,@RequestHeader("Authorization") String stringToken){
        if(!sessionManager.exitsSession(sessionId)) throw new NoSessionFoundException(sessionId);
        if(!tokenService.isValidAdminToken(new TokenModel(stringToken))) throw new AuthorizationServiceException("Only Admins can request SurveyTemplates");

        return surveyManager.getTemplates().values();
    }

    @GetMapping("/admin/session/{sessionId}/surveys")
    public Collection<Survey> getSessionSurveys(@PathVariable int sessionId,@RequestHeader("Authorization") String stringToken){
        if(!sessionManager.exitsSession(sessionId)) throw new NoSessionFoundException(sessionId);
        if(!tokenService.isValidAdminToken(new TokenModel(stringToken))) throw new AuthorizationServiceException("Only Admins can request SurveyTemplates");

        if(getSessionSurveyList(sessionId).values().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No surveys exist");
        }
        return getSessionSurveyList(sessionId).values();
    }

    @GetMapping("/admin/session/{sessionId}/survey/create/{templateId}")
    public void create(@PathVariable int sessionId, @PathVariable int templateId,@RequestHeader("Authorization") String stringToken){
        if(!sessionManager.exitsSession(sessionId)) throw new NoSessionFoundException(sessionId);
        if(!tokenService.isValidAdminToken(new TokenModel(stringToken))) throw new AuthorizationServiceException("Only Admins can request SurveyTemplates");

        SurveyTemplate template = getTemplateById(templateId);
        if(template != null){
            createSurveyInSession(sessionId, template);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("SurveyTemplate-ID %d not exists", templateId));
        }
    }

    @PostMapping("/admin/session/{sessionId}/survey/create")
    public SurveyTemplate create(@PathVariable int sessionId, @RequestBody SurveyTemplate template,@RequestHeader("Authorization") String stringToken){
        if(!sessionManager.exitsSession(sessionId)) throw new NoSessionFoundException(sessionId);
        if(!tokenService.isValidAdminToken(new TokenModel(stringToken))) throw new AuthorizationServiceException("Only Admins can request SurveyTemplates");

        template.setId(surveyManager.getTemplates().size()+1);
        surveyManager.getTemplates().put(template.getId(), template);
        createSurveyInSession(sessionId, template);
        return template;
    }

    @PostMapping("/participant/session/{sessionId}/survey/{surveyId}/answer")
    public void setAnswer(@PathVariable int sessionId, @PathVariable int surveyId, @RequestBody MessageModel messageModel,@RequestHeader("Authorization") String stringToken){

        if(!sessionManager.exitsSession(sessionId)) throw new NoSessionFoundException(sessionId);
        TokenModel tokenModel = new TokenModel(stringToken);
        int participantId;
        int participantSessionId;
        try{
            participantId = tokenService.getTokenValue("id",tokenModel).asInt();
            participantSessionId = tokenService.getTokenValue("sessionId",tokenModel).asInt();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The token from the Authentication Header was bad");
        }

        if(sessionId != participantSessionId) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The Token was created for another session");


        Survey survey = getSessionSurveyList(sessionId).get(surveyId);
        if(survey != null){
            survey.addAnswer(messageModel.getText(),participantId);
            surveyService.onUpdate(sessionId, survey);
        }
    }

    private SurveyTemplate getTemplateById(int templateId){
        return surveyManager.getTemplates().get(templateId);
    }

    //removes the need to check if sessionSurveys returns null for a given key
    private HashMap<Integer, Survey> getSessionSurveyList(int sessionId){
        HashMap<Integer,HashMap<Integer,Survey>> sessionSurveys = surveyManager.getSessionSurveys();
        if(!sessionSurveys.containsKey(sessionId)) sessionSurveys.put(sessionId, new HashMap<>());
        return sessionSurveys.get(sessionId);
    }

    private void addSurveyToSession(int sessionId, Survey survey){
        HashMap<Integer,HashMap<Integer,Survey>> sessionSurveys = surveyManager.getSessionSurveys();
        if(!sessionSurveys.containsKey(sessionId)) sessionSurveys.put(sessionId, new HashMap<>());
        sessionSurveys.get(sessionId).put(survey.getId(), survey);
    }

    public void updateSurvey(int sessionId, Survey survey){
        surveyManager.getSessionSurveys().get(sessionId).put(survey.getId(), survey);
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
